package com.imdrissi.demo.services;

import org.springframework.stereotype.Service;

@Service
public class SearchService {

    AlbumAsyncService albumAsyncService;
    BookAsyncService bookAsyncService;

    public SearchService(AlbumAsyncService albumAsyncService, BookAsyncService bookAsyncService) {
        this.albumAsyncService = albumAsyncService;
        this.bookAsyncService = bookAsyncService;
    }
}
