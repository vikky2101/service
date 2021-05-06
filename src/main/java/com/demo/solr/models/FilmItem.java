package com.demo.solr.models;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Builder
@AllArgsConstructor
public class FilmItem implements Item{

  private int id;

  private String name;

  private String directed_by;

  private List<String> genre;

  private Date initial_release_date;

  private float score;

  public FilmItem() {

  }
}
