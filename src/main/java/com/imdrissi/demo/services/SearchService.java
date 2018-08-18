package com.imdrissi.demo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchService {

  AlbumAsyncService albumAsyncService;
  BookAsyncService bookAsyncService;

  public SearchService(AlbumAsyncService albumAsyncService, BookAsyncService bookAsyncService) {
    this.albumAsyncService = albumAsyncService;
    this.bookAsyncService = bookAsyncService;
  }
}
