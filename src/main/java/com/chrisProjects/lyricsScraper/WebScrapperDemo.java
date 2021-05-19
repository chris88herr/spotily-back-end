package com.chrisProjects.lyricsScraper;

import com.chrisProjects.lyricsScraper.Utils.Utls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebScrapperDemo {

//    public static void main(String[] args){
//        runDemo();
//    }

    public static void runDemo(){
        ExecutorService executor= Executors.newFixedThreadPool(4);
//        String[] artists = {"red hot chili peppers", "the marias", "brahny", "yellow days", "drake", "bad bunny", "ozuna", "coldplay", "t-pain"};
//        String[] artists = {"feng suave", "sabino", "luna luna", "ed maverick"};
//        for(String artist:artists) {
//            WebScraper webScraper = new WebScraper(artist, 5, 10);
//            executor.submit(()->webScraper.runScrapper());
//        }
        WebScraper singleSongWebScraper = new WebScraper("the beatles", "yesterday");
        executor.submit(()->singleSongWebScraper.runScrapper());
        Utls.shutDownExecutor(executor);
    }

}
