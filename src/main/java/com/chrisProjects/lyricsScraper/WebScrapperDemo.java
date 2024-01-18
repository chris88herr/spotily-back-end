package com.chrisProjects.lyricsScraper;

import com.chrisProjects.lyricsScraper.Utils.Utls;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebScrapperDemo {

    public static void main(String[] args){
//        runDemo();
//    	String url = "https://genius.com/adele-hello-lyrics";
//    	try {
//			Document doc = Jsoup.connect(url)
//					.userAgent("Chrome")
//					.timeout(5*1000).get();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    public static void runDemo(){
        ExecutorService executor= Executors.newFixedThreadPool(4);
//        String[] artists = {"red hot chili peppers", "the marias", "brahny", "yellow days", "drake", "bad bunny", "ozuna", "coldplay", "t-pain"};
//        String[] artists = {"feng suave", "sabino", "luna luna", "ed maverick"};
//        for(String artist:artists) {
//            WebScraper webScraper = new WebScraper(artist, 5, 10);
//            executor.submit(()->webScraper.runScrapper());
//        }
        WebScraper singleSongWebScraper = new WebScraper("adele", "hello");
        executor.submit(()->singleSongWebScraper.runScrapper());
        Utls.shutDownExecutor(executor);
    }

}
