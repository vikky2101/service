package com.demo.mappers;

import com.demo.models.Payload;
import com.demo.models.QueryMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilmItemSolrIndexMapper implements QueryMapper {

  public SolrInputDocument map(Payload payload) {
    SolrInputDocument solrInputDocument = new SolrInputDocument();
    Map<String, Object> params = payload.getParams();
    if (params.containsKey("id")) {
      solrInputDocument.addField("id", params.get("id"));
    }
    if (params.containsKey("name")) {
      solrInputDocument.addField("name", params.get("name"));
    }
    if (params.containsKey("directed_by")) {
      solrInputDocument.addField("directed_by", params.get("directed_by"));
    }

    if (params.containsKey("initial_release_date")) {
      solrInputDocument.addField("initial_release_date", params.get("initial_release_date"));

    }
    if (params.containsKey("genre")) {
      solrInputDocument.addField("genre", params.get("genre"));
    }
    return solrInputDocument;

  }

}
