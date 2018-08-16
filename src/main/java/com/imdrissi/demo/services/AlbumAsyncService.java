package com.imdrissi.demo.services;

import com.imdrissi.demo.config.AsyncConf;
import com.imdrissi.demo.domain.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AlbumAsyncService {


    @Async(AsyncConf.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<List<Album>> getdAll() {
        List<Album> result = Arrays.asList(new Album("8-mile", "eminem"), new Album("tina", "tinariwen"));

        return CompletableFuture.supplyAsync(() -> new ArrayList<>(result));

    }

}
