package com.imdrissi.demo.controllers;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Book;
import com.imdrissi.demo.services.BookAsyncService;
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
    basePath = "/api/books",
    value = "Book",
    description = "Book api",
    produces = "application/json")
@RestController
@RequestMapping(value = "/api/books")
@Slf4j
public class BookController {

  private final BookAsyncService bookService;
  private final int search_limit;

  public BookController(
      BookAsyncService bookService, @Value("${service.book.search.limit}") int search_limit) {
    this.bookService = bookService;
    this.search_limit = search_limit;
  }

  @ApiOperation(
      value = "view list of books related to given term",
      response = Book.class,
      responseContainer = "List")
  @Async(AsyncConf.TASK_EXECUTOR_CONTROLLER)
  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CompletableFuture<ResponseEntity> getAlbumsByTerm(@RequestParam String term) {
    return bookService
        .getBooksByTerm(term, search_limit)
        .<ResponseEntity>thenApply(ResponseEntity::ok)
        .exceptionally(handleGetBooksException);
  }

  private static Function<Throwable, ResponseEntity> handleGetBooksException =
      throwable -> {
        log.error("Unable to retrieve books", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      };
}
