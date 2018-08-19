package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Book;
import com.imdrissi.demo.domain.BookResponse;
import lombok.extern.slf4j.Slf4j;
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
public class BookAsyncService {

  private RestOperations restTemplate;
  private String api_search_url = "https://www.googleapis.com/books/v1/volumes?q=%s&maxResults=%s";

  public BookAsyncService(RestOperations restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<Book>> getBooksByTerm(String term, int resultLimit) {
    log.info("getting books for term {}, results limited to {}", term, resultLimit);
    String url = String.format(api_search_url, term, resultLimit);

    HttpEntity<BookResponse> httpEntity = new HttpEntity<>(new BookResponse());

    BookResponse bookResponse =
        restTemplate
            .exchange(
                url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<BookResponse>() {})
            .getBody();

    List<Book> albums =
        bookResponse
            .getResults()
            .stream()
            .sorted(Comparator.comparing(Book::getTitle))
            .collect(Collectors.toList());

    return CompletableFuture.supplyAsync(() -> new ArrayList<>(albums));
  }
}
