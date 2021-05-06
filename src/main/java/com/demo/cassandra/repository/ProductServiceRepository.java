package com.demo.cassandra.repository;

import com.demo.cassandra.model.ProductDataModel;
import org.springframework.data.repository.CrudRepository;

public interface ProductServiceRepository extends
    CrudRepository<ProductDataModel, Long> {

}
