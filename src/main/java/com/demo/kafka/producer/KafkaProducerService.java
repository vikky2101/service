package com.demo.kafka.producer;

import com.demo.kafka.producer.KafkaProducer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaProducerService<T> {

  private final @NonNull KafkaProducer kafkaProducer;

  public void add(String topicName, T t) {
    log.info("Adding Data in kafka {}", t.toString());
    Message<T> message = MessageBuilder.withPayload(t).setHeader(KafkaHeaders.TOPIC, topicName)
        .build();
    kafkaProducer.sendMessage(message);
  }

}
