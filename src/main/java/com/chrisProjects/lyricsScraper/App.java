package com.chrisProjects.lyricsScraper;

import com.chrisProjects.lyricsScraper.Utils.HibernateUtil;
import com.chrisProjects.lyricsScraper.Utils.Utls;
import com.chrisProjects.lyricsScraper.dao.GenreDaoIml;
import com.chrisProjects.lyricsScraper.models.Genre;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
//        new WebScrapperDemo().runDemo();
    }
}
