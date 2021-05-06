package com.demo.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Payload {

  Map<String, Object> params;

  public Payload(Map<String, Object> params) {
    this.params = params;
  }

  public Payload() {
  }

  public Object get(String key) {
    return params.get(key);
  }

  public <T> T getKey(String key, Class<T> type) {
    return type.cast(this.params.get(key));
  }


}
