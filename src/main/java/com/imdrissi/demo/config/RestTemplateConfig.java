package com.imdrissi.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class RestTemplateConfig {

  @Bean
  @Qualifier("itunesRestTemplate")
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        MappingJackson2HttpMessageConverter jsonConverter =
            (MappingJackson2HttpMessageConverter) converter;
        jsonConverter.setObjectMapper(new ObjectMapper());
        jsonConverter.setSupportedMediaTypes(
            Arrays.asList(
                new MediaType(
                    "application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET),
                new MediaType(
                    "text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
      }
    }
    return restTemplate;
  }
}
