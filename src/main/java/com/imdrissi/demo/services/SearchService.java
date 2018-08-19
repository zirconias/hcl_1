package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.domain.Book;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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

  @Timed(value = "service.api.search", description = "measure hcl search api")
  @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
  public CompletableFuture<List<?>> search(String term, int resultLimit) {
    log.info("searching for term {}...", term);
    CompletableFuture<List<Book>> books = bookAsyncService.getBooksByTerm(term, resultLimit);
    CompletableFuture<List<Album>> albums = albumAsyncService.getAlbumsByTerm(term, resultLimit);

    CompletableFuture<List<?>> allOf = allOf(books, albums);

    log.info("returned {} items", allOf.join().size());
    return allOf;
  }

  static CompletableFuture<List<?>> allOf(CompletableFuture<?>... cfs) {
    CompletableFuture<List<?>> cf =
        CompletableFuture.allOf(cfs)
            .thenApply(
                ignore -> Stream.of(cfs).map(CompletableFuture::join).collect(Collectors.toList()));

    // to return one list of items
    List listOfAll = new ArrayList();
    cf.join().forEach(e -> listOfAll.addAll((Collection) e));
    return CompletableFuture.completedFuture(listOfAll);
  }
}
