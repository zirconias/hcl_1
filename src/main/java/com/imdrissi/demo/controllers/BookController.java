package com.imdrissi.demo.controllers;

import com.imdrissi.demo.services.BookAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/books")
@Slf4j
public class BookController {

  private final BookAsyncService bookService;

  public BookController(BookAsyncService bookService) {
    this.bookService = bookService;
  }
}
