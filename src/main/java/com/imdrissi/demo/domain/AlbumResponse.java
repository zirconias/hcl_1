package com.imdrissi.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumResponse {
  private String resultCount;

  private List<Album> results;
}
