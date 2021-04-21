package com.chrisProjects.lyricsScraper.Utils;

import com.chrisProjects.lyricsScraper.WebScraper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Utls {
    public static void writeToFile (String baseDir, String artist, String data){
        String currDir = System.getProperty("user.dir");
        String[] songAndLyrics = data.split(WebScraper.SONG_LYRIC_DELIMITER);
        String songName = songAndLyrics[0].replace(" ", "_").replace("?", "");
        String lyrics = songAndLyrics[1];
        String songNamefixed = songName.replaceAll("\\/","");
        String dirPat = currDir + "\\" +baseDir+ "\\" + artist;
        File directory = new File(dirPat);
        if(!directory.exists()){
            directory.mkdirs();
        }
        String filePath = directory + "\\"+songNamefixed.replaceAll(" ", "_")+".txt";
        FileWriter fileWriter=null;
        File file = null;
        try{
            file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            fileWriter = new FileWriter(new File(filePath));
            fileWriter.write(lyrics);
        }
        catch(IOException e){
            System.out.println("couldnt write to file:" + filePath);
            e.printStackTrace();
        }
        finally {
            try{
                if(fileWriter!=null)
                    fileWriter.close();
            }
            catch (IOException e){
                System.out.println("couldt close file");
            }
        }
    }
    public static void shutDownExecutor(ExecutorService executor){
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("executor hit the fan bruh");
        }
    }
}
