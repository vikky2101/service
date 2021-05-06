package com.demo.mappers;

import com.demo.models.SolrResponseMapper;
import com.demo.solr.models.TypeAheadItem;
import java.util.List;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;

@Component
public class TypeAheadItemSolrResponseMapper implements SolrResponseMapper<TypeAheadItem> {

  @Override
  public TypeAheadItem map(SolrDocument solrDocument) {
    TypeAheadItem TypeAheadItem = new TypeAheadItem();
    if (solrDocument.containsKey("cat")) {
      TypeAheadItem.setCat(((List<String>) solrDocument.get("cat")));
    }
    TypeAheadItem.setScore((Float) solrDocument.get("score"));
    return TypeAheadItem;
  }
}
