package com.demo.solr.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
public class DebugInfo {

  private String query;
  private Object explain;

  public DebugInfo() {

  }
}
