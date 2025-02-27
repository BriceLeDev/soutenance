package com.soutenence.publiciteApp.payement.Response;

import com.soutenence.publiciteApp.payement.enums.Currency;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
public record TransactionResponse(
         Integer amount,
         Currency currency,
         String status,
         String payment_method,
         String username,
         String userEmail,
         Date localDateTimeTrans,
         long abonnementId
) {
}
