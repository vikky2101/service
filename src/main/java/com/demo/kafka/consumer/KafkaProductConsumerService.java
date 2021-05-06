package com.demo.kafka.consumer;

import com.demo.models.Payload;
import com.demo.service.FilmWrapperService;
import com.demo.service.ProductWrapperService;
import com.demo.solr.custom.SolrIndexingService;
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
public class KafkaProductConsumerService {

  private final @NonNull ProductWrapperService productWrapperService;

  @KafkaListener(topics = {"product_service"}, groupId = "group_id")
  public void listenProduct(
      @org.springframework.messaging.handler.annotation.Payload Payload payload,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition)
      throws IOException, SolrServerException {
    log.info("#### -> Consumed message -> {} from topic {} partition {}", topic,
        payload.toString(), partition);
    productWrapperService.transformAddCassandra(payload);
    productWrapperService.transformAddSolr(topic, payload);

  }

}
