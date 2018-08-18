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

  @JsonProperty(value = "artistName")
  private String artists;

  private Type type = Type.ALBUM;
}
