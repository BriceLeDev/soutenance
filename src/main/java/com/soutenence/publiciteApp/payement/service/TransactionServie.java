package com.soutenence.publiciteApp.payement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.Message;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.enums.NiveauPayement;
import com.soutenence.publiciteApp.enums.TypeMessage;
import com.soutenence.publiciteApp.payement.Response.CheckTransactionResponse;
import com.soutenence.publiciteApp.payement.Response.LinkPayementRespons;
import com.soutenence.publiciteApp.payement.Response.NotificationRequestBody;
import com.soutenence.publiciteApp.payement.entite.Facture;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import com.soutenence.publiciteApp.payement.enums.Currency;
import com.soutenence.publiciteApp.payement.enums.TransactionStatus;
import com.soutenence.publiciteApp.payement.repositories.FactureRepository;
import com.soutenence.publiciteApp.payement.repositories.TransactionRepository;
import com.soutenence.publiciteApp.payement.request.CheckTransactionRequest;
import com.soutenence.publiciteApp.payement.request.LinkPayementRequest;
import com.soutenence.publiciteApp.payement.request.TransactionRequest;
import com.soutenence.publiciteApp.repository.AbonnementRepositorie;
import com.soutenence.publiciteApp.repository.MessageRepositorie;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
@Slf4j
@Service
public class TransactionServie {

    private final WebClient.Builder builder;
    private final AbonnementRepositorie abonnementRepositorie;
    private final TransactionRepository transactionRepository;
    private final FactureRepository factureRepository;
    private final RestTemplate restTemplate;
    private final HttpServletResponse httpServletResponse;
    private final MessageRepositorie messageRepositorie;
    @Value("${app.integrator.apiKey}")
    private String apiKey;
    @Value("${app.integrator.siteIid}")
    private String siteId;
    @Value("${app.integrator.paymentUrl}")
    private String apiUrl;
    @Value("${app.integrator.ngrockUrl}")
    private String ngrockUrl;

    public TransactionServie(WebClient.Builder builder, AbonnementRepositorie abonnementRepositorie, TransactionRepository transactionRepository, FactureRepository factureRepository, RestTemplate restTemplate, HttpServletResponse httpServletResponse, MessageRepositorie messageRepositorie) {
        this.builder = builder;
        this.abonnementRepositorie = abonnementRepositorie;
        this.transactionRepository = transactionRepository;
        this.factureRepository = factureRepository;
        this.restTemplate = restTemplate;
        this.httpServletResponse = httpServletResponse;
        this.messageRepositorie = messageRepositorie;
    }

    public LinkPayementRespons send(TransactionRequest request) throws JsonProcessingException {
        Abonnement abonnement = abonnementRepositorie.findById(request.abonnementId())
                .orElseThrow(()-> new EntityNotFoundException("Cet abonnement n existe pas"));
        Transaction transaction = Transaction.builder()
                .dateTrans(new Date())
                .transactionId(generateCombinedTransactionId())
                .amount(request.amount())
                .currency(Currency.XOF)
                .description(request.description())
                .status(String.valueOf(TransactionStatus.CREATED))
                .abonnement(abonnement)
                .build();
        this.transactionRepository.save(transaction);
       // log.info("**** This is transaction before payment **** " + transaction.toString());

        abonnement.setMtnPayer(request.amount());

        if (abonnement.getPrix() == request.amount()){
            abonnement.setNiveauAbn(NiveauPayement.TOTAL);
            abonnement.setMtnRest(abonnement.getPrix()-request.amount());
        }else {
            abonnement.setNiveauAbn(NiveauPayement.PARTIEL);
            abonnement.setMtnRest((abonnement.getPrix()-request.amount()));
        }
        this.abonnementRepositorie.save(abonnement);
        //Envoye de la requete pour recupérer le lien de payement
        LinkPayementRequest linkPayementRequest = LinkPayementRequest.builder()
                .transaction_id(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .channels(request.chanel())
                .description(transaction.getDescription())
                .apikey(apiKey)
                .site_id(siteId)
                .notify_url("https://3f71-2c0f-f0f8-6bf-f700-4566-335a-eb8b-6637.ngrok-free.app/api/v1/payment/notification")
                .return_url("https://3f71-2c0f-f0f8-6bf-f700-4566-335a-eb8b-6637.ngrok-free.app/api/v1/payment/return")
                .build();
        log.info("This is return url ***** " + linkPayementRequest.getReturn_url());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Créer un corps JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody2 = objectMapper.writeValueAsString(linkPayementRequest);
        String url =apiUrl;
        HttpEntity<String> request1 = new HttpEntity<>(jsonBody2, headers);

        LinkPayementRespons response = restTemplate.postForObject(url, request1, LinkPayementRespons.class);
       return response;

    }

    public void checkTransaction(CheckTransactionRequest transactionRequest ) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Créer un corps JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody2 = objectMapper.writeValueAsString(transactionRequest);
        String url ="https://api-checkout.cinetpay.com/v2/payment/check";
        HttpEntity<String> request1 = new HttpEntity<>(jsonBody2, headers);
        CheckTransactionResponse response = restTemplate.postForObject(url, request1, CheckTransactionResponse.class);
        log.info(String.valueOf(response));
      //  String errorCode = response.getCode();
        String message = "";
        //recupérer l enum en lui donnant le code qui se trouve dans la réponse
        assert response != null;
        TransactionStatus status = TransactionStatus.fromCode(response.getCode());
        // recupérer la transaction
        Transaction transaction = this.transactionRepository.findByTransactionId(transactionRequest.getTransaction_id());
       //Modifier le status de la transaction
        transaction.setStatus(status.name());
        transactionRepository.save(transaction);
        log.info("This is transaction after payment **** " + transaction.toString());
        message=status.getDescription();
        log.info(response.toString());
        log.info(response.getCode());
        log.info(message);
        log.info("on est à la dernier ligne de retour et après la création de la facture ");


        Facture facture = new Facture();
        facture.setMtnPayer(transaction.getAmount());
        facture.setMtnRestant((transaction.getAbonnement().getMtnRest()));
        facture.setDatePayment(LocalDate.now());
        facture.setTransaction(transaction.getTransactionId());
        facture.setDateDebAbn(transaction.getAbonnement().getDateDebut());
        facture.setDateFinAbn(transaction.getAbonnement().getDateFin());
        facture.setDateAbn(transaction.getAbonnement().getDateAbn());
        facture.setMtnTotal(transaction.getAbonnement().getPrix());
        facture.setReference(generateCombinedTransactionId().substring(0,8));

        Message messageForUser = new Message();
        messageForUser.setReceiver(transaction.getAbonnement().getUser());
        messageForUser.setAbonnement(transaction.getAbonnement());
        messageForUser.setLocalDateTime(LocalDateTime.now());
        messageForUser.setType(TypeMessage.CONFIRMATION);
        String text = "Merci pour votre abonnement. \n Vous aurez une confirmation dans 72h après une vérification.";
        messageForUser.setMessage(text);
        this.messageRepositorie.save(messageForUser);
        factureRepository.save(facture);
        log.info("Facture créer ");
        // vérifier si le code est 00 pour rediriger l utilisateur vers
        // la page d abonnement ou different de 00 alors vers une page d'erreur
       // String redirectUrl = "http://localhost:4200/customer/abonnement/"+transaction.getAbonnement().getId()+"?transactionId=" + transactionRequest.getTransaction_id() ;
        String redirectUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:4200/customer/abonnement/")
                .queryParam("abonnement",transaction.getAbonnement().getId())
                .queryParam("transactionId",transaction.getTransactionId())
                .build()
                .toString();
            //httpServletResponse.setHeader("Location", redirectUrl);
        //log.info("redirect url ******** " + redirectUrl);

      /*  if (Objects.equals(status.getCode(), "00")){
            Facture facture = new Facture();
            facture.setAbonnement(transaction.getAbonnement());
            facture.setMtnPayer(transaction.getAmount());
            facture.setMtnRestant(transaction.getAbo *nnement().getMtnRest());
            facture.setAbonnement(transaction.getAbonnement());
            facture.setDatePayment(LocalDate.now());
            facture.setReference(generateCombinedTransactionId().substring(0,14));
        }*/
        // Effectuer la redirection vers l'abonnement qui concerne la transaction
            httpServletResponse.sendRedirect(redirectUrl);
    }
    public void checkStatusNotify(NotificationRequestBody notificationRequestBody) {
        System.out.print(notificationRequestBody.getCpm_error_message());
        log.info(String.valueOf(notificationRequestBody));
    }
    private String generateCombinedTransactionId() {
        long timestamp = System.currentTimeMillis();
        return UUID.randomUUID().toString() + "-" + timestamp;
    }

    private Integer getMtnRestant(Integer prixAbnmt, Integer MtnPayer){
        int price = prixAbnmt.compareTo(MtnPayer);
        return price;
    }

}
