package com.demo.cassandra.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("productDatamodel")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDataModel implements IDataModel {

  @PrimaryKey
  private long id;
  private String name;
  private List<String> cat;
  private String author;
  private String series_t;
  private int sequence_i;
  private boolean inStock;
  private double price;
  private int pages_i;


}
