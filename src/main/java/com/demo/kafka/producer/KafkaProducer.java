package com.demo.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaProducer {

  @Autowired
  private KafkaTemplate<String, Message> kafkaTemplate;

  public void sendMessage(Message message) {
    log.info(String.format("#### -> Producing message -> %s", message));
    ListenableFuture<SendResult<String, Message>> future =
        kafkaTemplate.send(message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {

      @Override
      public void onSuccess(SendResult<String, Message> result) {
        log.info("Sent message=[" + message +
            "] with offset=[" + result.getRecordMetadata().offset() + "]");
      }

      @Override
      public void onFailure(Throwable ex) {
        log.info("Unable to send message=["
            + message + "] due to : " + ex.getMessage());
      }
    });
  }

}
