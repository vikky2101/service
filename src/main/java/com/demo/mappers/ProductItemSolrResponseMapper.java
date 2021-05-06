package com.demo.mappers;

import com.demo.models.SolrResponseMapper;
import com.demo.solr.models.ProductItem;
import java.util.List;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;

@Component
public class ProductItemSolrResponseMapper implements SolrResponseMapper<ProductItem> {

  @Override
  public ProductItem map(SolrDocument solrDocument) {
    ProductItem productItem = new ProductItem();
    if (solrDocument.containsKey("id")) {
      productItem.setId(Long.parseLong((String) solrDocument.get("id")));
    }
    if (solrDocument.containsKey("name")) {
      productItem.setName((String) solrDocument.get("name"));
    }
    if (solrDocument.containsKey("cat")) {
      productItem.setCat(((List<String>) solrDocument.get("cat")));
    }
    if (solrDocument.containsKey("author")) {
      productItem.setAuthor((String) solrDocument.get("author"));
    }
    if (solrDocument.containsKey("series_t")) {
      productItem.setSeries_t((String) solrDocument.get("series_t"));
    }
    if (solrDocument.containsKey("sequence_i")) {
      productItem.setSequence_i((Integer) solrDocument.get("sequence_i"));
    }
    if (solrDocument.containsKey("inStock")) {
      productItem.setInStock((Boolean) solrDocument.get("inStock"));
    }
    if (solrDocument.containsKey("pages_i")) {
      productItem.setPages_i((Integer) solrDocument.get("pages_i"));
    }
    if (solrDocument.containsKey("price")) {
      productItem.setPrice((Float) solrDocument.get("price"));
    }
    productItem.setScore((Float) solrDocument.get("score"));
    return productItem;
  }
}
