package com.imdrissi.demo.controllers;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import com.imdrissi.demo.services.AlbumAsyncService;
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
    basePath = "/api/albums",
    value = "Album",
    description = "Album api",
    produces = "application/json")
@RestController
@RequestMapping(value = "/api/albums")
@Slf4j
public class AlbumController {

  private final AlbumAsyncService albumService;
  private final int search_limit;

  public AlbumController(
      AlbumAsyncService albumService, @Value("${service.album.search.limit}") int search_limit) {
    this.albumService = albumService;
    this.search_limit = search_limit;
  }

  @ApiOperation(
      value = "view list of albums related to given term",
      response = Album.class,
      responseContainer = "List")
  @Async(AsyncConf.TASK_EXECUTOR_CONTROLLER)
  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CompletableFuture<ResponseEntity> getAlbumsByTerm(@RequestParam String term) {
    return albumService
        .getAlbumsByTerm(term, search_limit)
        .<ResponseEntity>thenApply(ResponseEntity::ok)
        .exceptionally(handleGetAlbumsException);
  }

  private static Function<Throwable, ResponseEntity> handleGetAlbumsException =
      throwable -> {
        log.error("Unable to retrieve albums", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      };
}
