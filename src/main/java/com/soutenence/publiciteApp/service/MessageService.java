package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.MessageMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.MessageResponse;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.repository.MessageRepositorie;
import com.soutenence.publiciteApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private  final MessageRepositorie messageRepositorie;
    private final UserRepository userRepository;
    private final MessageMapperClass messageMapperClass;

    public MessageService(MessageRepositorie messageRepositorie, UserRepository userRepository, MessageMapperClass messageMapperClass) {
        this.messageRepositorie = messageRepositorie;
        this.userRepository = userRepository;
        this.messageMapperClass = messageMapperClass;
    }


    public List<MessageResponse> getMessageByUser(String userid) {
        User user = this.userRepository.findByEmailIgnoreCase(userid)
                .orElseThrow(()->new EntityNotFoundException("Elément non trouvé"));
        return this.messageRepositorie.findAllByReceiverOrderByLocalDateTimeDesc(user)
                .stream().map(messageMapperClass::ToMessageResponse).toList();
    }

    public List<MessageResponse> getAllMessage() {
            return this.messageRepositorie.findAll(Sort.by(Sort.Direction.DESC,"localDateTime")).stream()
                    .map(messageMapperClass::ToMessageResponse)
                    .toList();
    }
}
