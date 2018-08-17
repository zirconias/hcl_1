package com.imdrissi.demo;

import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.domain.Book;
import com.imdrissi.demo.services.AlbumAsyncService;
import com.imdrissi.demo.services.BookAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
@Slf4j
public class ApplicationRunnerr implements ApplicationRunner {


    private final AlbumAsyncService albumService;
    private final BookAsyncService bookService;

    public ApplicationRunnerr(AlbumAsyncService albumService, BookAsyncService bookService) {
        this.albumService = albumService;
        this.bookService = bookService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {


        CompletableFuture<List<Book>> books = bookService.getdAll();
        CompletableFuture<List<Album>> albums = albumService.getdAll();
        CompletableFuture<List<Album>> albumsWithTerm = albumService.getAlbums("hell");

        CompletableFuture.allOf(books, albums, albumsWithTerm).join();

        log.info("size of books is {}",books.get().size());
        log.info("size of albums is {}",albums.get().size());

    }
}
