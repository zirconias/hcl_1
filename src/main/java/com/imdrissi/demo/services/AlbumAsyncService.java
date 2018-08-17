package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AlbumAsyncService {

  private RestOperations restTemplate;

  public AlbumAsyncService(RestTemplateBuilder restTemplateBuilder) {

    //    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    //    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    //    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    //    messageConverters.add(converter);
    //    restTemplateBuilder.messageConverters(messageConverters);

      MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
      mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.ALL));
      restTemplateBuilder.additionalMessageConverters(mappingJackson2HttpMessageConverter);

    this.restTemplate = restTemplateBuilder.build();
  }

  @Async
  public CompletableFuture<List<Album>> getAlbums(String term) {
    log.info("getting albums for term {}", term);
    String url = String.format("http://itunes.apple.com/search?term=%s&limit=5", term);

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<List<Album>> httpEntity = new HttpEntity<>(new ArrayList<Album>(), headers);

    ResponseEntity<?> response =
        restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Album>>() {});
    System.out.println("hello");
    //List<Album> albums = response.getBody();
return null;
    //return CompletableFuture.supplyAsync(() -> new ArrayList<>(albums));
  }

  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<Album>> getdAll() {
    List<Album> result =
        Arrays.asList(new Album("8-mile", "eminem"), new Album("tina", "tinariwen"));
    return CompletableFuture.supplyAsync(() -> new ArrayList<>(result));
  }
}
