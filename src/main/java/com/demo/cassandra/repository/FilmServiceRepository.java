package com.demo.cassandra.repository;

import com.demo.cassandra.model.FilmDataModel;
import org.springframework.data.repository.CrudRepository;

public interface FilmServiceRepository extends
    CrudRepository<FilmDataModel, Integer> {

}
