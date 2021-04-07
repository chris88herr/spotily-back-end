package com.chrisProjects.lyricsScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */


public class App 
{


    public static void main( String[] args )
    {
        ExecutorService executor= Executors.newFixedThreadPool(4);
        String[] artists = {"red hot chili peppers", "the marias", "brahny", "yellow days"};
        for(String artist:artists) {
            WebScraper webScraper = new WebScraper(artist, 5, 10);
            executor.submit(()->webScraper.runScrapper());
        }
        Utls.shutDownExecutor(executor);


    }





}
