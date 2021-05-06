package com.demo.solr.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
@EnableSolrRepositories(
    basePackages = "com.demo.solr")
public class SolrConfig {

  @Bean
  public SolrClient solrClient() {
    SolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr")
        .withResponseParser(new XMLResponseParser()).build();
    return solrClient;
  }

  @Bean
  public SolrTemplate solrTemplate(SolrClient client) throws Exception {
    return new SolrTemplate(client);
  }
}
