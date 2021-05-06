package com.demo.service;

import com.demo.cassandra.model.FilmDataModel;
import com.demo.cassandra.repository.CassandraFilmService;
import com.demo.constants.MapperBean;
import com.demo.models.Payload;
import com.demo.models.QueryMapper;
import com.demo.models.QueryMapperFactory;
import com.demo.solr.custom.SolrAliasService;
import com.demo.solr.custom.SolrIndexingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmWrapperService {

  private final @NonNull CassandraFilmService cassandraFilmService;

  private final @NonNull QueryMapperFactory queryMapperFactory;

  private final @NonNull SolrAliasService solrAliasService;

  private final @NonNull SolrIndexingService solrIndexingService;

  @Async
  public void transformAddSolr(String collectionName, Payload payload) {
    log.info("ThreadName:" + Thread.currentThread().getName());
    solrIndexingService.index(collectionName, payload);
    solrAliasService.switchAlias(collectionName);

  }

  @Async
  public void transformAddCassandra(Payload payload) {
    log.info("ThreadName:" + Thread.currentThread().getName());
    QueryMapper<FilmDataModel> queryMapper = queryMapperFactory
        .instance(MapperBean.FILM_SERVICE.getCassandraQueryMapper());
    cassandraFilmService.create(queryMapper.map(payload));
  }
}
