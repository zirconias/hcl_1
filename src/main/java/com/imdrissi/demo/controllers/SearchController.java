package com.imdrissi.demo.controllers;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.services.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Api(
    basePath = "/api/search",
    value = "Search",
    description = "Search in book and album apis",
    produces = "application/json")
@RestController
@RequestMapping(value = "/api/search")
@Slf4j
public class SearchController {

  private final SearchService searchService;
  private final int search_limit;

  public SearchController(
      SearchService searchService, @Value("${service.search.limit}") int searchLimit) {
    this.searchService = searchService;
    this.search_limit = searchLimit;
  }

  @ApiOperation(value = "search books and albums related to given term")
  @Async(AsyncConf.TASK_EXECUTOR_CONTROLLER)
  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CompletableFuture<ResponseEntity> search(@RequestParam String term) {
    return searchService
        .search(term, search_limit)
        .<ResponseEntity>thenApply(ResponseEntity::ok)
        .exceptionally(handleSearchException);
  }

  private static Function<Throwable, ResponseEntity> handleSearchException =
      throwable -> {
        log.error("Unable to search..", throwable);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      };
}
