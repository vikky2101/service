package com.demo.models;

public interface QueryMapper<T> {

  T map(Payload payload) ;
}
