package com.chrisProjects.lyricsScraper;

import com.chrisProjects.lyricsScraper.Utils.Utls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebScrapperDemo {

    public static void runDemo(){
        ExecutorService executor= Executors.newFixedThreadPool(4);
        String[] artists = {"red hot chili peppers", "the marias", "brahny", "yellow days"};
        for(String artist:artists) {
            WebScraper webScraper = new WebScraper(artist, 5, 10);
            executor.submit(()->webScraper.runScrapper());
        }
        Utls.shutDownExecutor(executor);
    }
}
