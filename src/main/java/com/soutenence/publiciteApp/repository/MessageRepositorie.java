package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.ResponseAndRequest.MessageResponse;
import com.soutenence.publiciteApp.entity.Message;
import com.soutenence.publiciteApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepositorie extends JpaRepository<Message,Long> {
    Message findByReceiver(User user);

    List<Message>findAllByReceiverOrderByLocalDateTimeDesc(User user);
}
