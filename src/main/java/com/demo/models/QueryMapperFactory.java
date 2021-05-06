package com.demo.models;

public interface QueryMapperFactory {

  QueryMapper instance(String queryMapperName);
}
