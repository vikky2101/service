package com.demo.cassandra.repository;

import com.demo.cassandra.model.FilmDataModel;
import com.demo.cassandra.repository.FilmServiceRepository;
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
public class CassandraFilmService {

  private final @NonNull FilmServiceRepository filmServiceRepository;

  public void create(FilmDataModel filmDataModel) {
    log.info("adding Data in casandra {}", filmDataModel.toString());
    filmServiceRepository.save(filmDataModel);
  }

  public Optional<FilmDataModel> getById(int id) {
    log.info("fetching Data from casandra for id {}", id);
    return filmServiceRepository.findById(id);
  }

  public void deletetById(int id) {
    log.info("deleting data from casandra for id {}, id");
    filmServiceRepository.deleteById(id);
  }

  public void deletetAll() {
    log.info("deleting all from casandra {} ");
    filmServiceRepository.deleteAll();
  }

  public List<FilmDataModel> getAll() {
    log.info("fetching all data from casandra");
    return (List<FilmDataModel>) filmServiceRepository.findAll();
  }
}
