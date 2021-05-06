package com.demo.solr.custom;

import com.demo.constants.MapperBean;
import com.demo.models.ResponseMapperFactory;
import com.demo.models.SolrResponseMapper;
import com.demo.solr.models.DebugInfo;
import com.demo.solr.models.ItemResponseWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SolrQueryService<T> {

  private final String LIVE = "_live";

  private final @NonNull SolrClient customSolrClient;

  private final @NonNull ResponseMapperFactory responseMapperFactory;

  public void deleteAll(String collectionName) {
    collectionName = collectionName.concat(LIVE);
    log.info("deleting all data from collection {}", collectionName);
    try {
      customSolrClient.deleteByQuery(collectionName, "*:*");
      customSolrClient.commit(collectionName);
    } catch (SolrServerException e) {
      log.error("Exception: {}", e);
    } catch (IOException e) {
      log.error("Exception: {}", e);
    }
  }

  public void deleteById(String collectionName, String id) {
    collectionName = collectionName.concat(LIVE);
    log.info("deleting data from collection {} with id {}", collectionName, id);
    try {
      customSolrClient.deleteById(collectionName, id);
      customSolrClient.commit(collectionName);
    } catch (SolrServerException e) {
      log.error("Exception {}", e);
    } catch (IOException e) {
      log.error("Exception {}", e);
    }
  }

  private SolrQuery getSolrQuery(Map<String, Object> params) {
    SolrQuery query = new SolrQuery();
    String queryParser = "edismax";
    if (params.containsKey("defType")) {
      queryParser = (String) params.get("defType");
    }
    query.set("defType", queryParser);
    String term = "*:*";
    if (params.containsKey("q")) {
      term = (String) params.get("q");
    }
    query.set("q", term);
    query.set("fl", "* score");
    String sort = Strings.EMPTY;
    if (params.containsKey("sort")) {
      sort = (String) params.get("sort");
      query.set("sort", sort);
    }
    String bq = Strings.EMPTY;
    if (params.containsKey("bq")) {
      bq = (String) params.get("bq");
      query.set("bq", bq);
    }
    if (params.containsKey("debug")) {
      query.set("debug", true);
    }
    String fq = Strings.EMPTY;
    if (params.containsKey("fq")) {
      fq = (String) params.get("fq");
      query.set("fq", fq);
    }
    return query;
  }

  private SolrQuery getTypeAheadQuery(Map<String, Object> params) {
    SolrQuery query = new SolrQuery();
    String queryParser = "edismax";
    if (params.containsKey("defType")) {
      queryParser = (String) params.get("defType");
    }
    query.set("defType", queryParser);
    query.set("qf", "name name_copy");
    String term = "*:*";
    if (params.containsKey("q")) {
      term = (String) params.get("q");
    }
    query.set("q", term);
    query.set("fl", "cat score");
    if (params.containsKey("debug")) {
      query.set("debug", true);
    }
    String fq = Strings.EMPTY;
    if (params.containsKey("fq")) {
      fq = (String) params.get("fq");
      query.set("fq", fq);
    }
    return query;
  }


  public ItemResponseWrapper query(String collectionName, Map<String, Object> params) {
    String queryCollectionName = collectionName.concat(LIVE);
    log.info("querying solr collection {} with params", queryCollectionName, params.toString());
    ItemResponseWrapper itemResponseWrapper = new ItemResponseWrapper();
    List<T> tList = Collections.emptyList();
    try {
      SolrQuery solrQuery = getSolrQuery(params);
      QueryResponse response = customSolrClient.query(queryCollectionName, solrQuery, METHOD.GET);
      List<SolrDocument> solrDocumentList = response.getResults();
      tList = new ArrayList<>();
      SolrResponseMapper<T> solrResponseMapper = responseMapperFactory
          .instance(MapperBean.getMap().get(collectionName).getSolrResponseMapper());
      log.info("solr collection {} ResponseMapper {} ", collectionName, solrResponseMapper);
      for (SolrDocument solrDocument : solrDocumentList) {
        tList.add(solrResponseMapper.map(solrDocument));
      }
      if (params.containsKey("debug")) {
        DebugInfo debugInfo = new DebugInfo();
        debugInfo.setQuery(
            "http://localhost:8983/solr/" + queryCollectionName + "/select" + solrQuery
                .toQueryString());
        itemResponseWrapper.setDebugInfo(debugInfo);
      }
    } catch (Exception exc) {
      log.error("Exception {}", exc);
    }
    itemResponseWrapper.setItems(tList);
    return itemResponseWrapper;
  }

  public ItemResponseWrapper typeAheadQuery(String collectionName, Map<String, Object> params) {
    log.info("querying solr collection {} with params", collectionName, params.toString());
    ItemResponseWrapper itemResponseWrapper = new ItemResponseWrapper();
    List<T> tList = Collections.emptyList();
    try {
      SolrQuery solrQuery = getTypeAheadQuery(params);
      QueryResponse response = customSolrClient.query(collectionName, solrQuery, METHOD.GET);
      List<SolrDocument> solrDocumentList = response.getResults();
      tList = new ArrayList<>();
      SolrResponseMapper<T> solrResponseMapper = responseMapperFactory
          .instance(MapperBean.getMap().get(collectionName).getSolrResponseMapper());
      log.info("solr collection {} ResponseMapper {} ", collectionName, solrResponseMapper);
      for (SolrDocument solrDocument : solrDocumentList) {
        tList.add(solrResponseMapper.map(solrDocument));
      }
      if (params.containsKey("debug")) {
        DebugInfo debugInfo = new DebugInfo();
        debugInfo.setQuery(
            "http://localhost:8983/solr/" + collectionName + "/select" + solrQuery
                .toQueryString());
        itemResponseWrapper.setDebugInfo(debugInfo);
      }
    } catch (Exception exc) {
      log.error("Exception {}", exc);
    }
    itemResponseWrapper.setItems(tList);
    return itemResponseWrapper;
  }

  public ItemResponseWrapper getAll(String collectionName) {
    String queryCollectionName = collectionName.concat(LIVE);
    log.info("getting all  data from collection {} ", queryCollectionName);
    ItemResponseWrapper itemResponseWrapper = new ItemResponseWrapper();
    List<T> tList = Collections.EMPTY_LIST;
    try {
      SolrQuery query = new SolrQuery();
      String term = "*:*";
      query.set("q", term);
      query.set("fl", "* score");
      QueryResponse queryResponse = customSolrClient.query(queryCollectionName, query, METHOD.GET);
      List<SolrDocument> solrDocumentList = queryResponse.getResults();
      tList = new ArrayList<>();
      SolrResponseMapper<T> solrResponseMapper = responseMapperFactory
          .instance(MapperBean.getMap().get(collectionName).getSolrResponseMapper());
      for (SolrDocument solrDocument : solrDocumentList) {
        tList.add(solrResponseMapper.map(solrDocument));
      }
    } catch (SolrServerException e) {
      log.error("Exception {}", e);
    } catch (IOException e) {
      log.error("Exception {}", e);
    }
    itemResponseWrapper.setItems(tList);
    return itemResponseWrapper;
  }

  public Optional<T> getById(String collectionName, String id) {
    String queryCollectionName = collectionName.concat(LIVE);
    log.info("getting data from collection {} with id {}", queryCollectionName, id);
    Optional<T> optionalT = (Optional<T>) Optional.ofNullable(Collections.EMPTY_LIST);
    try {
      Optional<SolrDocument> optionalSolrDocument = Optional
          .ofNullable(customSolrClient.getById(queryCollectionName, id));
      SolrResponseMapper<T> solrResponseMapper = responseMapperFactory
          .instance(MapperBean.getMap().get(collectionName).getSolrResponseMapper());
      if (optionalSolrDocument.isPresent()) {
        optionalT = Optional.ofNullable(solrResponseMapper.map(optionalSolrDocument.get()));
      }
    } catch (SolrServerException e) {
      log.error("Exception {}", e);
    } catch (IOException e) {
      log.error("Exception {}", e);
    }
    return optionalT;

  }

}
