package com.imdrissi.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
  private String title;
  private Type type = Type.BOOK;
  private String author;

  public Book(String title, String authors) {
    this.title = title;
    this.author = authors;
  }
}
