package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.LigneAbonnementResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.MessageResponse;
import com.soutenence.publiciteApp.entity.LigneAbonnement;
import com.soutenence.publiciteApp.entity.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageMapperClass {


    public MessageResponse ToMessageResponse(Message message){
        return MessageResponse.builder()
                .message(message.getMessage())
                .receiverMail(message.getReceiver().getEmail())
                .abonnementId(message.getAbonnement().getId())
                .localDateTime(message.getLocalDateTime())
                .type(message.getType())
                .build();
    }
}
