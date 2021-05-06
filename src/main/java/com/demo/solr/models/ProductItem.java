package com.demo.solr.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Builder
@AllArgsConstructor
public class ProductItem implements Item {

  private long id;

  private String name;

  private List<String> cat;

  private String author;

  private String series_t;

  private int sequence_i;

  private boolean inStock;

  private float price;

  private int pages_i;

  private float score;

  public ProductItem() {

  }

}
