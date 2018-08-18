package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.domain.AlbumResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AlbumAsyncService {

  private RestOperations restTemplate;

  public AlbumAsyncService(@Qualifier("itunesRestTemplate") RestOperations restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<Album>> getAlbums(String term) {
    log.info("getting albums for term {}", term);
    String url = String.format("http://itunes.apple.com/search?term=%s&entity=album&limit=5", term);

    HttpEntity<AlbumResponse> httpEntity = new HttpEntity<AlbumResponse>(new AlbumResponse());

    AlbumResponse AlbumResponse =
        restTemplate
            .exchange(
                url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<AlbumResponse>() {})
            .getBody();

    List<Album> albums = (List<Album>) AlbumResponse.getResults();

    return CompletableFuture.supplyAsync(() -> new ArrayList<>(albums));
  }
}
