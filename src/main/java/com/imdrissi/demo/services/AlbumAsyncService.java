package com.imdrissi.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.domain.response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AlbumAsyncService {

  private RestOperations restTemplate;

  MediaType t = MediaType.valueOf("text/javascript;charset=utf-8");


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                jsonConverter.setSupportedMediaTypes(Arrays.asList(
                        new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET),
                        new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)
                        )
                );
                //jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
            }
        }
        return restTemplate;
    }

  public AlbumAsyncService(RestTemplateBuilder restTemplateBuilder) {

    //        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    //        MappingJackson2HttpMessageConverter converter = new
    // MappingJackson2HttpMessageConverter();
    //        converter.setSupportedMediaTypes(Collections.singletonList(t));
    //        messageConverters.add(converter);
    //        restTemplateBuilder.messageConverters(messageConverters);

    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();

    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
        Arrays.asList(MediaType.APPLICATION_JSON, t));
    restTemplateBuilder.additionalMessageConverters(mappingJackson2HttpMessageConverter);

    this.restTemplate = restTemplateBuilder.build();
  }

  @Async
  public CompletableFuture<List<Album>> getAlbums(String term) {
    log.info("getting albums for term {}", term);
    String url = String.format("http://itunes.apple.com/search?term=%s&entity=album&limit=5", term);

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<response> httpEntity = new HttpEntity<>(new response(), headers);

//    response responese = restTemplate().getForObject(url, response.class);

//    ResponseEntity<response> response =
//        restTemplate.exchange(
//            url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<response>() {});

      HttpEntity<response> httpEntity2 = new HttpEntity<response>(new response(), headers);

      ResponseEntity<response> response =
              restTemplate().exchange(
                      url, HttpMethod.GET, httpEntity2, new ParameterizedTypeReference<response>() {});

    System.out.println("hello");
    // List<Album> albums = response.getBody();
    return null;
    // return CompletableFuture.supplyAsync(() -> new ArrayList<>(albums));
  }

  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<Album>> getdAll() {
    List<Album> result =
        Arrays.asList(new Album("8-mile", "eminem"), new Album("tina", "tinariwen"));
    return CompletableFuture.supplyAsync(() -> new ArrayList<>(result));
  }
}
