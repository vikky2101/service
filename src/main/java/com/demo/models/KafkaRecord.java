package com.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Builder
@Slf4j
@ToString
@EqualsAndHashCode
public class KafkaRecord {

  String topicName;

  private Payload payload;

  public KafkaRecord() {

  }

  public KafkaRecord(String topicName, Payload payload) {
    this.topicName = topicName;
    this.payload = payload;
  }

}
