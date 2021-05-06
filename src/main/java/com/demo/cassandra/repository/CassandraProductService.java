package com.demo.cassandra.repository;

import com.demo.cassandra.model.ProductDataModel;
import com.demo.cassandra.repository.ProductServiceRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CassandraProductService {

  private final @NonNull ProductServiceRepository productServiceRepository;

  public void create(ProductDataModel productDataModel) {
    log.info("adding Data in casandra {}", productDataModel.toString());
    productServiceRepository.save(productDataModel);
  }

  public Optional<ProductDataModel> getById(long id) {
    log.info("fetching Data from casandra for id {}", id);
    return productServiceRepository.findById(id);
  }

  public void deletetById(long id) {
    log.info("deleting data from casandra for id {}, id");
    productServiceRepository.deleteById(id);
  }

  public void deletetAll() {
    log.info("deleting all from casandra {} ");
    productServiceRepository.deleteAll();
  }

  public List<ProductDataModel> getAll() {
    log.info("fetching all data from casandra");
    return (List<ProductDataModel>) productServiceRepository.findAll();
  }
}
