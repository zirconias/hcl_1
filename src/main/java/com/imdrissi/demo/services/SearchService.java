package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SearchService {

  AlbumAsyncService albumAsyncService;
  BookAsyncService bookAsyncService;

  public SearchService(AlbumAsyncService albumAsyncService, BookAsyncService bookAsyncService) {
    this.albumAsyncService = albumAsyncService;
    this.bookAsyncService = bookAsyncService;
  }

  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<?>> search(String term, int resultLimit) {
    log.info("searching for term {}...", term);
    CompletableFuture<List<Book>> books = bookAsyncService.getBooksByTerm(term, resultLimit);
    CompletableFuture<List<Album>> albums = albumAsyncService.getAlbumsByTerm(term, resultLimit);

    CompletableFuture<List<?>> allOf = allOf(books, albums);

    List al = new ArrayList();
    List<?> result =
        books
            .thenCombine(
                albums,
                (l1, l2) -> {
                  al.addAll(l2);
                  al.addAll(l1);
                  return al;
                  //                  return Stream.concat(l1.stream(),
                  // l2.stream()).collect(Collectors.toList());
                })
            .join();

    //    List all = new ArrayList();
    //    allOf.thenAccept(
    //        l -> {
    //          l.forEach(System.out::println);
    //        });

    //    return allOf.join();

    return allOf;
  }

  static CompletableFuture<List<?>> allOf(CompletableFuture<?>... cfs) {
    return CompletableFuture.allOf(cfs)
        .thenApply(
            ignore -> Stream.of(cfs).map(CompletableFuture::join).collect(Collectors.toList()));
  }
}
