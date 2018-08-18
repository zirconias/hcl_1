package com.imdrissi.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

  @JsonProperty(value = "collectionName")
  private String title;

  private Type type = Type.ALBUM;
  private String artists; // todo: list of artists ?

  public Album(String title, String artists) {
    this.title = title;
    this.artists = artists;
  }
}
