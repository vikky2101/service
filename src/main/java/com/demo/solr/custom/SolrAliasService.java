package com.demo.solr.custom;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SolrAliasService {

  public void switchAlias(String collectionName) {
    // switch collection alias
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8983/solr/admin/collections?action=LISTALIASES&wt=json";
    ResponseEntity<Map> result = restTemplate.getForEntity(url, Map.class);
    Map<String, Map> map = result.getBody();
    Map<String, String> aliasMap = (HashMap) map.get("aliases");
    String oldCollectionName = aliasMap.get(collectionName + "_shadow");
    String newCollectionName = aliasMap.get(collectionName + "_live");
    String liveAliasUrl =
        "http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=" + collectionName
            + "_live&collections="
            + oldCollectionName;
    result = restTemplate.getForEntity(liveAliasUrl, Map.class);
    String shadowAliasUrl =
        "http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=" + collectionName
            + "_shadow&collections="
            + newCollectionName;
    result = restTemplate.getForEntity(shadowAliasUrl, Map.class);
  }

}
