package com.imdrissi.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Album {

    private String title;
    private Type type = Type.ALBUM;
    private String artists;//todo: list of artists ?

    public Album(String title, String artists) {
        this.title = title;
        this.artists = artists;
    }
}
