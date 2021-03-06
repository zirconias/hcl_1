package com.imdrissi.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
  private String title;
  private Type type = Type.BOOK;
  private String authors;

  @JsonProperty("volumeInfo")
  private void unpack(Map<String, Object> volumeInfo) {
    this.title = (String) volumeInfo.get("title");
    this.authors = String.join(",", (List<String>) volumeInfo.get("authors"));
  }
}
