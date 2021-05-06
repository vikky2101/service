package com.demo.controller;

import com.demo.solr.custom.SolrQueryService;
import com.demo.solr.models.ItemResponseWrapper;
import com.demo.solr.models.ProductItem;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/service/typeahead")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TypeAheadController {

  private final @NonNull SolrQueryService solrQueryService;

  private final String READ_COLLECTION_NAME = "typeahead_service";

  @GetMapping(value = "/solr/query")
  public Map<String, Object> getTypeAheadSuggestions(@RequestParam Map<String, Object> map) {
    Map<String, Object> responseMap = new HashMap<>();
    ItemResponseWrapper<ProductItem> itemItemResponseWrapper = solrQueryService
        .typeAheadQuery(READ_COLLECTION_NAME, map);
    responseMap.put("items", itemItemResponseWrapper.getItems());
    responseMap.put("noOfResults", itemItemResponseWrapper.getItems().size());
    if (itemItemResponseWrapper.getDebugInfo() != null) {
      responseMap.put("debugInfo", itemItemResponseWrapper.getDebugInfo());
    }
    return responseMap;
  }

}
