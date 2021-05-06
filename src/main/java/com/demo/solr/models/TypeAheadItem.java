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
public class TypeAheadItem implements Item {

  private List<String> cat;

  private float score;

  public TypeAheadItem() {

  }

}
