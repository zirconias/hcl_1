package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.domain.AlbumResponse;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlbumAsyncService {

  private RestOperations restTemplate;
  private String api_search_url = "http://itunes.apple.com/search?term=%s&entity=album&limit=%s";

  public AlbumAsyncService(@Qualifier("itunesRestTemplate") RestOperations restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Timed(value = "service.api.album.search", description = "measure itunes search api")
  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<Album>> getAlbumsByTerm(String term, int resultLimit) {
    log.info("getting albums for term {}, results limited to {}", term, resultLimit);
    String url = String.format(api_search_url, term, resultLimit);

    HttpEntity<AlbumResponse> httpEntity = new HttpEntity<>(new AlbumResponse());

    AlbumResponse albumResponse =
        restTemplate
            .exchange(
                url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<AlbumResponse>() {})
            .getBody();

    List<Album> albums =
        albumResponse
            .getResults()
            .stream()
            .sorted(Comparator.comparing(Album::getTitle))
            .collect(Collectors.toList());

    return CompletableFuture.supplyAsync(() -> new ArrayList<>(albums));
  }
}
