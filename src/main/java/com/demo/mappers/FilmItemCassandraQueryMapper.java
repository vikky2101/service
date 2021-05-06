package com.demo.mappers;

import com.demo.models.Payload;
import com.demo.models.QueryMapper;
import com.demo.cassandra.model.FilmDataModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilmItemCassandraQueryMapper implements QueryMapper {

  DateFormat format = new SimpleDateFormat("yyyy-mm-dd");

  public FilmDataModel map(Payload payload) {
    FilmDataModel filmDataModel = new FilmDataModel();
    try {
      Map<String, Object> params = payload.getParams();
      if (params.containsKey("id")) {
        filmDataModel.setId((Integer) params.get("id"));
      }
      if (params.containsKey("name")) {
        filmDataModel.setName((String) params.get("name"));
      }
      if (params.containsKey("directed_by")) {
        filmDataModel.setDirected_by((String) params.get("directed_by"));
      }


      if (params.containsKey("initial_release_date")) {
        filmDataModel.setInitial_release_date(format.parse(
            (String) params.get("initial_release_date")));
      }
      if (params.containsKey("genre")) {
        filmDataModel.setGenre((List<String>) params.get("genre"));
      }
    } catch (ParseException exc) {
      log.error("exc");
    }
    return filmDataModel;

  }

}
