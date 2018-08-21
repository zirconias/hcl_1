package com.imdrissi.demo;

import com.imdrissi.demo.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
  @Autowired private WebApplicationContext webCtx;

  @Autowired RestTemplateBuilder builder;

  @LocalServerPort int port;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webCtx).apply(springSecurity()).build();
  }

  @Test
  public void testGETBooks() {
    String url = "http://localhost:" + port + "/api/books?term=test";
    TestRestTemplate template = new TestRestTemplate("user", "user");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<List> entity = new HttpEntity<>(headers);

    List<Book> out =
        template
            .exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Book>>() {})
            .getBody();

    Assert.assertNotNull(out);
  }
}
