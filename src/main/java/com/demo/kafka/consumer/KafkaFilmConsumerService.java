package com.demo.kafka.consumer;

import com.demo.models.Payload;
import com.demo.service.FilmWrapperService;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaFilmConsumerService {

  private final @NonNull FilmWrapperService filmWrapperService;

  @KafkaListener(topics = {"film_service"}, groupId = "group_id")
  public void listenFilm(
      @org.springframework.messaging.handler.annotation.Payload Payload payload,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition)
      throws IOException, SolrServerException {
    log.info("#### -> Consumed message -> {} from topic {} partition {}", topic,
        payload.toString(), partition);
    filmWrapperService.transformAddCassandra(payload);
    filmWrapperService.transformAddSolr(topic, payload);
  }
}
