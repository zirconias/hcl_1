package com.imdrissi.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdrissi.demo.domain.Album;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumControllerTest {

  @Autowired private WebApplicationContext webCtx;

  @Autowired RestTemplateBuilder builder;

  private RestTemplate template;

  @LocalServerPort int port;

  private MockMvc mockMvc;

  @Before
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webCtx).apply(springSecurity()).build();
  }

  @PostConstruct
  public void initialize() {
    template = builder.basicAuthorization("user", "user").build();
    for (HttpMessageConverter<?> converter : template.getMessageConverters()) {
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
  }

  @Test
  public void testGETAboutController() {
    String url = "http://localhost:" + port + "/api/albums?term=test";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<List> entity = new HttpEntity<>(headers);

    List<Album> out =
        template
            .exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Album>>() {})
            .getBody();

    Assert.assertNotNull(out);
  }
}
