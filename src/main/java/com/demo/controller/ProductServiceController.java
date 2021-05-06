package com.demo.controller;

import com.demo.cassandra.model.ProductDataModel;
import com.demo.cassandra.repository.CassandraProductService;
import com.demo.kafka.producer.KafkaProducerService;
import com.demo.models.KafkaRecord;
import com.demo.models.Payload;
import com.demo.solr.custom.SolrQueryService;
import com.demo.solr.models.ItemResponseWrapper;
import com.demo.solr.models.ProductItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/service/products")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductServiceController {

  private final @NonNull KafkaProducerService<Payload> kafkaProducerService;

  private final @NonNull CassandraProductService cassandraProductService;

  private final @NonNull SolrQueryService solrQueryService;

  private final String READ_COLLECTION_NAME = "product_service";

  @PostMapping(value = "/publish")
  public ResponseEntity<String> sendMessageToKafkaTopic(@RequestBody KafkaRecord kafkaRecord) {
    this.kafkaProducerService.add(kafkaRecord.getTopicName(), kafkaRecord.getPayload());
    return ResponseEntity.status(201)
        .body("Resource Created with payload");
  }

  @GetMapping(value = "/cassandra/getAll")
  public Map<String, Object> getAllCassandra() {
    Map<String, Object> map = new HashMap<>();
    List<ProductDataModel> productDataModelList = cassandraProductService.getAll();
    map.put("items", productDataModelList);
    map.put("noOfResults", productDataModelList.size());
    return map;
  }

  @GetMapping(value = "/solr/getAll")
  public Map<String, Object> getAllSolr() {
    Map<String, Object> responseMap = new HashMap<>();
    ItemResponseWrapper<ProductItem> itemItemResponseWrapper = solrQueryService
        .query(READ_COLLECTION_NAME, responseMap);
    responseMap.put("items", itemItemResponseWrapper.getItems());
    responseMap.put("noOfResults", itemItemResponseWrapper.getItems().size());
    if (itemItemResponseWrapper.getDebugInfo() != null) {
      responseMap.put("debugInfo", itemItemResponseWrapper.getDebugInfo());
    }
    return responseMap;
  }

  @GetMapping(value = "/solr/query")
  public Map<String, Object> getSolrCustomQuery(@RequestParam Map<String, Object> map) {
    Map<String, Object> responseMap = new HashMap<>();
    ItemResponseWrapper<ProductItem> itemItemResponseWrapper = solrQueryService
        .query(READ_COLLECTION_NAME, map);
    responseMap.put("items", itemItemResponseWrapper.getItems());
    responseMap.put("noOfResults", itemItemResponseWrapper.getItems().size());
    if (itemItemResponseWrapper.getDebugInfo() != null) {
      responseMap.put("debugInfo", itemItemResponseWrapper.getDebugInfo());
    }
    return responseMap;
  }


  @GetMapping(value = "/get")
  public Map<String, Object> getById(@RequestParam long id) {
    Map<String, Object> map = new HashMap<>();
    Optional<ProductDataModel> optionalDataModel = cassandraProductService.getById(id);
    Optional<ProductItem> optionalProduct = solrQueryService
        .getById(READ_COLLECTION_NAME, String.valueOf(id));
    if (optionalDataModel.isPresent()) {
      map.put("cassandra", optionalDataModel.get());
    } else {
      map.put("cassandra", "");
    }
    if (optionalProduct.isPresent()) {
      map.put("solr", optionalProduct.get());
    } else {
      map.put("solr", "");
    }
    return map;
  }

  @DeleteMapping(value = "/delete")
  public ResponseEntity<String> deleteById(@RequestParam int id) {
    cassandraProductService.deletetById(id);
    solrQueryService.deleteById(READ_COLLECTION_NAME, String.valueOf(id));
    return ResponseEntity.status(200).body("Deleted from cassandra solr");
  }

  @DeleteMapping(value = "/cassandra/deleteAll")
  public ResponseEntity<String> deleteAllCassandra() {
    cassandraProductService.deletetAll();
    return ResponseEntity.status(200).body("Deleted from cassandra");
  }

  @DeleteMapping(value = "/solr/deleteAll")
  public ResponseEntity<String> deleteAllSolr() {
    solrQueryService.deleteAll(READ_COLLECTION_NAME);
    return ResponseEntity.ok().body("Deleted from solr");
  }
}