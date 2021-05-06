package com.demo.mappers;

import com.demo.models.Payload;
import com.demo.models.QueryMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductItemSolrIndexMapper implements QueryMapper {

  public SolrInputDocument map(Payload payload) {
    SolrInputDocument solrInputDocument = new SolrInputDocument();
    Map<String, Object> params = payload.getParams();
    if (params.containsKey("id")) {
      solrInputDocument.addField("id", params.get("id"));
    }
    if (params.containsKey("name")) {
      solrInputDocument.addField("name", params.get("name"));
    }
    if (params.containsKey("cat")) {
      solrInputDocument.addField("cat", params.get("cat"));
    }
    if (params.containsKey("author")) {
      solrInputDocument.addField("author", params.get("author"));
    }
    if (params.containsKey("series_t")) {
      solrInputDocument.addField("series_t", params.get("series_t"));
    }
    if (params.containsKey("sequence_i")) {
      solrInputDocument.addField("sequence_i", params.get("sequence_i"));
    }
    if (params.containsKey("inStock")) {
      solrInputDocument.addField("inStock", params.get("inStock"));
    }
    if (params.containsKey("price")) {
      solrInputDocument.addField("price", params.get("price"));
    }
    if (params.containsKey("pages_i")) {
      solrInputDocument.addField("pages_i", params.get("pages_i"));
    }

    return solrInputDocument;

  }

}
