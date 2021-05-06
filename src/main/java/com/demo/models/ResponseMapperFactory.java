package com.demo.models;

public interface ResponseMapperFactory {

  SolrResponseMapper instance(String responseMapper);
}
