package com.imdrissi.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Book {

  private String title;
  private Type type = Type.BOOK;
  private String authors; // todo: list of authors

  public Book(String title, String authors) {
    this.title = title;
    this.authors = authors;
  }
}
