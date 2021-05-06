package com.demo.solr.models;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class ItemResponseWrapper<T> {

  private List<T> items;

  private DebugInfo debugInfo;

}
