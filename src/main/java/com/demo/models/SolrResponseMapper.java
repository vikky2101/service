package com.demo.models;

import org.apache.solr.common.SolrDocument;

public interface SolrResponseMapper<T> {

  T map(SolrDocument solrDocument);
}
