package com.soutenence.publiciteApp.payement.controler;

import com.soutenence.publiciteApp.ResponseAndRequest.FactureResponse;
import com.soutenence.publiciteApp.payement.service.FactureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("facture")
public class FactureControler {

    private final FactureService factureService;

    public FactureControler(FactureService factureService) {
        this.factureService = factureService;

    }

    @GetMapping("/get-facture")
    public FactureResponse getFactureByTrans(@RequestParam("transId") String transId){
        return this.factureService.getFactureByTrans(transId);
    }

    @GetMapping("/get-facture-by-abonnement")
    public FactureResponse getFactureByAbonnementid(@RequestParam("abonnementId") long abonnementId){
        return this.factureService.getFactureByAbonnementid(abonnementId);
    }
}
