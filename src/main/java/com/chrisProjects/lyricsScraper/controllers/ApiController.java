package com.chrisProjects.lyricsScraper.controllers;

import com.chrisProjects.lyricsScraper.models.SongLyricsResponse;
import com.chrisProjects.lyricsScraper.models.SongLyricsRequest;
import com.chrisProjects.lyricsScraper.services.SongLyricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private SongLyricsService lyricsService;

    @GetMapping(value="/songLyrics")
    public ResponseEntity sayHello(@RequestParam(name="songName", required=true) String songName,
                                   @RequestParam(name="artist", required=true) String artistName
                                   ){
        //Enable CORS
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");

        String lyrics = lyricsService.getSongLyrics(artistName, songName);
        if(lyrics==null){
            System.out.println("No content");
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        }
        SongLyricsResponse body = new SongLyricsResponse(artistName, songName, lyrics);



        return new ResponseEntity(body, headers, HttpStatus.OK);

    }
}
