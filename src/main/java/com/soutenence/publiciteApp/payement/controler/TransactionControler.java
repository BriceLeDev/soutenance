package com.soutenence.publiciteApp.payement.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soutenence.publiciteApp.payement.DateConvertisseur;
import com.soutenence.publiciteApp.payement.Response.CheckTransactionResponse;
import com.soutenence.publiciteApp.payement.Response.LinkPayementRespons;
import com.soutenence.publiciteApp.payement.Response.NotificationRequestBody;
import com.soutenence.publiciteApp.payement.repositories.TransactionRepository;
import com.soutenence.publiciteApp.payement.request.CheckTransactionRequest;
import com.soutenence.publiciteApp.payement.request.TransactionRequest;
import com.soutenence.publiciteApp.payement.service.TransactionServie;
import com.soutenence.publiciteApp.repository.AbonnementRepositorie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("payment")

public class TransactionControler {

    private static final Logger log = LoggerFactory.getLogger(TransactionControler.class);
    private final DateConvertisseur dateConvertisseur;
    private final TransactionServie transactionServie;
    private final AbonnementRepositorie abonnementRepositorie;
    private final TransactionRepository transactionRepository;
    @Value("${app.integrator.apiKey}")
    private String apiKey;
    @Value("${app.integrator.siteIid}")
    private String siteId;
    public TransactionControler(DateConvertisseur dateConvertisseur, TransactionServie transactionServie, AbonnementRepositorie abonnementRepositorie, TransactionRepository transactionRepository) {
        this.dateConvertisseur = dateConvertisseur;
        this.transactionServie = transactionServie;
        this.abonnementRepositorie = abonnementRepositorie;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/link")
    public LinkPayementRespons getPaymentLink(TransactionRequest request) throws JsonProcessingException {
        Date theDate = dateConvertisseur.convert(request.dateTrans());
        return this.transactionServie.send(request);
    }
    @GetMapping("/notification")
    public ResponseEntity<?> checkStatusNotify(){
        return  ResponseEntity.ok().build();
    }
    @PostMapping("/notification")
    public ResponseEntity<?> checkStatusNotify(
            @RequestHeader("x-token") String token,
            @RequestBody NotificationRequestBody NotificationRequestBody,
            @RequestParam("cpm_trans_id ") String cpm_trans_id
    ){
        this.transactionServie.checkStatusNotify(NotificationRequestBody);
        log.info(cpm_trans_id);
        log.info(token);
        return  ResponseEntity.ok().build();
    }
    @GetMapping("/return")
    public ResponseEntity<?> returnTo(){
        return  ResponseEntity.ok().build();
    }
    @PostMapping("/return")
    public ResponseEntity<?> returnTo(
            @RequestParam("transaction_id") String transaction_id,
            @RequestParam("token") String token
    ) throws IOException {
        CheckTransactionRequest  transactionRequest = new CheckTransactionRequest();
        transactionRequest.setTransaction_id(transaction_id);
        transactionRequest.setSite_id(siteId);
        transactionRequest.setApikey(apiKey);
        log.info("le retour clicke");
        this.transactionServie.checkTransaction(transactionRequest);
        log.info("le retour");
        return  ResponseEntity.ok().build();
    }
    /*@PostMapping("/check-transaction")
    public CheckTransactionResponse checkTransaction(@RequestBody CheckTransactionRequest transactionRequest) throws IOException {
        return  this.transactionServie.checkTransaction(transactionRequest);

    }*/





}
