package com.demo.solr.custom;

import com.demo.constants.MapperBean;
import com.demo.models.Payload;
import com.demo.models.QueryMapper;
import com.demo.models.QueryMapperFactory;
import javax.print.DocFlavor.STRING;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SolrIndexingService {

  private final @NonNull SolrClient customSolrClient;

  private final @NonNull QueryMapperFactory queryMapperFactory;

  private static final String SHADOW = "_shadow";

  public boolean index(String collectionName, Payload payload) {
    try {
      QueryMapper<SolrInputDocument> queryMapper = queryMapperFactory
          .instance(MapperBean.getMap().get(collectionName).getSolrIndexMapper());
      collectionName = collectionName.concat(SHADOW);
      log.info("adding doc to solr collection {} mapper {} params {}", collectionName,
          queryMapper, payload.getParams().toString());
      SolrInputDocument solrInputDocument = queryMapper.map(payload);
      log.info("SolrInputDocument {}", solrInputDocument.toString());
      UpdateResponse updateResponse = customSolrClient
          .add(collectionName, solrInputDocument);
      customSolrClient.commit(collectionName);
      return true;
    } catch (Exception exc) {
      log.error("Exception while indexing {}", exc);
      return false;
    }
  }

}
