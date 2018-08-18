package com.imdrissi.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum Type {
  ALBUM("album"),
  BOOK("book");

  private final String value;
}
