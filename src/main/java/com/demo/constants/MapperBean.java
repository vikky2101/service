package com.demo.constants;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum MapperBean {

  FILM_SERVICE("film_service", "filmItemSolrIndexMapper", "filmItemCassandraQueryMapper",
      "filmItemSolrQueryMapper",
      "filmItemSolrResponseMapper"),
  PRODUCT_SERVICE("product_service", "productItemSolrIndexMapper",
      "productItemCassandraQueryMapper",
      "productItemSolrQueryMapper",
      "productItemSolrResponseMapper"),
  TYPEAHEAD_SERVICE("typeahead_service", "null",
      "null",
      "productItemSolrQueryMapper",
      "typeAheadItemSolrResponseMapper");;


  private String solrCollectionName;
  private String solrIndexMapper;
  private String cassandraQueryMapper;
  private String solrQueryMapper;
  private String solrResponseMapper;

  private static final Map<String, MapperBean> map;

  static {
    map = new HashMap<>();
    for (MapperBean key : MapperBean.values()) {
      map.put(key.solrCollectionName, key);
    }
  }

  public String getCassandraQueryMapper() {
    return cassandraQueryMapper;
  }

  public String getSolrQueryMapper() {
    return solrQueryMapper;
  }

  public String getSolrResponseMapper() {
    return solrResponseMapper;
  }

  public String getSolrIndexMapper() {
    return solrIndexMapper;
  }

  public String getSolrCollectionName() {
    return solrCollectionName;
  }

  MapperBean(String solrCollectionName, String solrIndexMapper, String cassandraQueryMapper,
      String solrQueryMapper,
      String solrResponseMapper) {
    this.solrCollectionName = solrCollectionName;
    this.solrIndexMapper = solrIndexMapper;
    this.cassandraQueryMapper = cassandraQueryMapper;
    this.solrQueryMapper = solrQueryMapper;
    this.solrResponseMapper = solrResponseMapper;
  }

  public static Map<String, MapperBean> getMap() {
    return map;
  }

}

