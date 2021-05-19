package com.chrisProjects.lyricsScraper;

import com.chrisProjects.lyricsScraper.Utils.Utls;
import com.chrisProjects.lyricsScraper.models.Song;
import com.chrisProjects.lyricsScraper.services.SongLyricsService;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class WebScraper {
    public final static String geniusBaseUrl = "https://genius.com";
    public static int lyricsSize = 0;
    public static final int DEFAULT_THREAD_NUMBER = 1;
    public static final String SONG_LYRIC_DELIMITER = "@@@@@@\n";
    public final static  String BASE_LYRICS_DIR ="songLyrics";
    private ExecutorService executor;
    private ExecutorService songLyricExecutor;
    private String artist = null;
    private String filesDirectory = null;
    private SongLyricsService lyricsService;
    private String singleSong;
    private String singleSongLyrics;


    private void debbuger(String method , String debug){
        System.out.println("DEBUG -- "+method+": "+debug);
    }

    public WebScraper(String artist){
        this.artist = artist.replace(" ", "-");
        executor = Executors.newFixedThreadPool(DEFAULT_THREAD_NUMBER);
        songLyricExecutor = Executors.newFixedThreadPool(DEFAULT_THREAD_NUMBER);
        lyricsService = new SongLyricsService();
    }

    public WebScraper(String artist, int numThreads, int numLyricThreads){
        this.artist = artist.replace(" ", "-");;
        executor = Executors.newFixedThreadPool(numThreads);
        songLyricExecutor = Executors.newFixedThreadPool(numLyricThreads);
        lyricsService = new SongLyricsService();

    }

    public WebScraper(String artist, String song){
        this.artist = artist.trim().replace(" ", "-");
        this.singleSong = song.trim().replace(" ", "-");
        executor = Executors.newFixedThreadPool(1);
        songLyricExecutor = Executors.newFixedThreadPool(1);
        lyricsService = new SongLyricsService();
    }

    public void runScrapper(){
        debbuger("","Starting scraping for " +artist);
        List<Future<String>> lyricsFutures;
        if(this.singleSong != null){
            lyricsFutures = getSingleLyrics(); //returns a list of futures to try to max code reuse
        }else {
            lyricsFutures = getLyrics();
        }
//        writeLyricsToFiles(lyricsFutures);
        writeToDatabase(lyricsFutures);
        debbuger("", "DONE with: "+artist+"\n");
    }

    private List<Future<String>> getSingleLyrics() {
        String songUrl = geniusBaseUrl + "/" + this.artist + "-" + this.singleSong + "-lyrics";
        Callable<String> callable = getSongLricsCallable(songUrl, this.singleSong);
        List<Future<String>> singleFuture = new ArrayList<>();
        singleFuture.add(songLyricExecutor.submit(callable));
        Utls.shutDownExecutor(songLyricExecutor);
        return singleFuture;
    }

    private void writeToDatabase(List<Future<String>> futures){
        for(Future<String> future: futures){
            try {
                String lyricsInfo = future.get();
                if (lyricsInfo==null) return;
                String[] songAndLyrics = lyricsInfo.split(WebScraper.SONG_LYRIC_DELIMITER);
                String songName = songAndLyrics[0].replace("?", "").replace("*","").replace("-", " ").trim();
                String lyrics = songAndLyrics[1].replace('-', ' ');
                if(singleSong!=null)
                    this.singleSongLyrics = lyrics;
                lyricsService.createLyricsRecord(artist, songName, lyrics);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Future<String>> getLyrics(){
        List<Future<String>> futures = new ArrayList<>();
        try{
            String url = geniusBaseUrl+"/artists/"+artist;
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            Element element = doc.select(".full_width_button").first();
            String hrefToSongs = element.attr("href"); //fetches URI for list of all songs
            String artistSongsUrl = geniusBaseUrl+hrefToSongs;
            Document artistSongsDoc = Jsoup.connect(artistSongsUrl).get();
            int paginations = getPaginations(artistSongsDoc);

            Map songLinksMap = null;

            if(paginations<1){
                songLinksMap = fetchMainPageSongLinksOnly(doc);
            }
            else {
                songLinksMap = fetchAllSongsLinks(artistSongsUrl, paginations, artistSongsDoc);
            }
            debbuger("GEYLYRICS","getting lyrics "+"{"+artist+"}");

            songLinksMap.forEach((song, songUrl) -> {
                Callable songLyricsCallable = getSongLricsCallable((String)songUrl, (String)song);
                futures.add( songLyricExecutor.submit(songLyricsCallable));
            });
        }
        catch(IOException e){
            if(e.getClass() == HttpStatusException.class){
                HttpStatusException httpStatusException = (HttpStatusException) e;
                int status = httpStatusException.getStatusCode();
                if(status == 404){
                    System.out.printf("Artist {%s} not found online :(\n", artist);
                }
            }
        }
        Utls.shutDownExecutor(songLyricExecutor);
        return futures;
    }

    private HashMap<String, String> fetchMainPageSongLinksOnly(Document artistMainPage){
        HashMap<String, String> songLinks = new HashMap<String, String>();
        Elements songCardDivs = artistMainPage.select(".mini_card_grid-song");
        for(Element div:songCardDivs){
            String href = div.select("a").attr("href");
            String song = div.select(".mini_card-title").text();
            if(song.contains("prod.")){
                int index= song.indexOf("prod.");
                song = song.substring(0,index);
            }
            songLinks.putIfAbsent(song, href);
        }
        return songLinks;
    }

    public String getLyricsForSong(String url, String songName) {
        {
            String parsedStr="";
            System.out.println("getting lyrics from url");
            try{
                Document doc = Jsoup.connect(url).timeout(5*1000).get();
                Elements pElements = doc.select("div[class^=\"Lyrics__Container\"]");
                for (Element e: pElements) {
                    parsedStr += e.text().replaceAll("\\[.*?]", "");
                }
            }
            catch (IOException e){
                debbuger("getLyrics", "FAILED TO FETCH "+url+" Song: "+songName);
            }
            finally {
                if(parsedStr.length()<1){
                    debbuger("GETLYRICS", "no lyircs for "+ songName);
                    return null;
                }
                String res  = songName+ SONG_LYRIC_DELIMITER +parsedStr;
                System.out.println(res);
                return res;
            }
        }
    }

    private void writeLyricsToFiles(List<Future<String>> lyricFutures){
        int size =0;
        for(Future<String> future:lyricFutures){
            try {
                String lyrics = future.get();
                if(lyrics!=null) {
                    size += future.get().getBytes().length;
                    Utls.writeToFile(BASE_LYRICS_DIR, artist, lyrics);

                }
                } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private int getPaginations(Document artistSongsDoc){

        Element div = artistSongsDoc.select("div.pagination").first();
        if(div==null){
            return 0;
        }
        Elements anchors = div.select("a");
        int paginations = Integer.parseInt( (String) anchors.get(anchors.size()-2).text());
        return paginations;
    }

    private HashMap<String, String> fetchAllSongsLinks(String url, int paginations, Document artistSongsDoc){
        String songUrl = artistSongsDoc.select("div.pagination").first().select("a").first().attr("href");

        String[] urlParts = songUrl.split("&");
        String[] withoutLAstPart = new String[urlParts.length-1];
        for(int i =0; i<urlParts.length-1; i++){
            withoutLAstPart[i] = urlParts[i];
        }
        String artist = withoutLAstPart[1].substring(3, withoutLAstPart[1].length());
        withoutLAstPart[2] = withoutLAstPart[2].substring(0, withoutLAstPart[2].length()-1);
        String finalUrl = String.join("&", withoutLAstPart);
        String paginationUrl = geniusBaseUrl + finalUrl;
        int pagination =1;
        HashMap<String, String> songLinks = new HashMap<>();

        while(pagination<=paginations){
            Runnable paginationRunnable = getPaginationRunnable(paginationUrl, pagination, artist, songLinks);
            executor.submit(paginationRunnable);
            pagination++;
        }
        Utls.shutDownExecutor(executor);
        return songLinks;
    }



    private void getSinglePagination(String paginationUrl, int pagination, String artist, Map songLinks ) {
        String finalUrl = paginationUrl+pagination;
        Document currentPaginationDoc = null;
        try {
            currentPaginationDoc = Jsoup.connect(finalUrl).get();
            Elements ul = currentPaginationDoc.getElementsByClass("song_list").select("li");
            for(Element li: ul){
                String hrefToSong = li.select("a").attr("href");
                String songName = li.text().replaceAll("\\(.*\\)", "");
                if(songName.length()==0)//we deleted songname, restore by just taking out parentheses
                {
                    songName =li.text().replaceAll("[()]","");
                }
                if(songName.contains("prod.")){
                    int index= songName.indexOf("prod.");
                    songName = songName.substring(0,index);
                }
                synchronized (songLinks) {
                    if (!hrefToSong.contains(artist))
                        continue;
                    songLinks.put(songName, hrefToSong);
                }
            }
        } catch (IOException e) {
            debbuger("GETSINGLEPAGINATION", "couldnt get pagination:"+ finalUrl);
        }

    }

    private  Callable<String> getSongLricsCallable(String songUrl, String song){
        return () -> getLyricsForSong((String)songUrl, (String)song);
    }

    private  Runnable getPaginationRunnable(final String url, int pagination, String artist, HashMap songLinks) {
        return () -> getSinglePagination(url, pagination, artist, songLinks);
    }
    public String getSingleSongLyrics() {
        return singleSongLyrics;
    }


}
