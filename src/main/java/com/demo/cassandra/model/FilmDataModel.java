package com.demo.cassandra.model;


import com.demo.cassandra.model.IDataModel;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("filmDatamodel")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class FilmDataModel implements IDataModel {

  @PrimaryKey
  private int id;
  private String name;
  private String directed_by;
  private Date initial_release_date;
  private List<String> genre;

  public FilmDataModel() {

  }

}
