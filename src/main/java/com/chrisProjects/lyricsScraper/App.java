package com.chrisProjects.lyricsScraper;

import com.chrisProjects.lyricsScraper.Utils.HibernateUtil;
import com.chrisProjects.lyricsScraper.Utils.Utls;
import com.chrisProjects.lyricsScraper.dao.GenreDaoIml;
import com.chrisProjects.lyricsScraper.models.Genre;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */


public class App 
{


    public static void main( String[] args )
    {
        GenreDaoIml genreDao = new GenreDaoIml();
        List<Genre> genres = genreDao.getAllGenres();
        if(genres!=null) {
            System.out.println(genres.size());
            for (Genre g : genres) {
                System.out.println(g);
            }
        }
        System.out.println("--------------------Get one Gnre----------------------");
        Genre genre = genreDao.getGenreByName("Reggaeton");
        if (genre!= null) {
            System.out.println(genre);
        }
        System.out.println("--------------------Get genre by ID----------------------");
        Genre genrebyId = genreDao.getGenreById(8);
        if (genre!= null) {
            System.out.println(genrebyId);
        }

        System.out.println("--------------------Update genre----------------------");
        genreDao.updateGenre(new Genre(8,"Reggaeton"));
        genreDao.updateGenre(new Genre(8,"Reggaeton update"));
//        System.out.println("--------------------Delete genre----------------------");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
        genreDao.updateGenre(new Genre(8,"Reggaeton second"));

    }





}
