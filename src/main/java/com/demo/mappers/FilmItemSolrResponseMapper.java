package com.demo.mappers;

import com.demo.models.SolrResponseMapper;
import com.demo.solr.models.FilmItem;
import com.demo.solr.models.ProductItem;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;

@Component
public class FilmItemSolrResponseMapper implements SolrResponseMapper<FilmItem> {

  DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

  @Override
  public FilmItem map(SolrDocument solrDocument) {
    FilmItem filmItem = new FilmItem();
    filmItem.setId(Integer.parseInt((String) solrDocument.get("id")));
    filmItem.setName((String) solrDocument.get("name"));
    filmItem.setDirected_by((String) solrDocument.get("directed_by"));
    try {
      filmItem.setInitial_release_date(
          dateFormat.parse(String.valueOf(solrDocument.get("initial_release_date"))));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    filmItem.setGenre((ArrayList<String>) solrDocument.get("genre"));
    filmItem.setScore((Float) solrDocument.get("score"));
    return filmItem;
  }
}
