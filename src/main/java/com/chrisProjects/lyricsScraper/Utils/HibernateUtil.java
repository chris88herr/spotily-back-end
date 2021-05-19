package com.chrisProjects.lyricsScraper.Utils;

import com.chrisProjects.lyricsScraper.models.Artist;
import com.chrisProjects.lyricsScraper.models.Genre;
import com.chrisProjects.lyricsScraper.models.Song;
import com.sun.xml.bind.api.impl.NameConverter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory==null){
            try{

                Configuration configuration = new Configuration();
                Properties props = new Properties();
                //setup hibernate properties for database communcaiton
                props.put(Environment.URL, System.getenv("DB_URL"));
                props.put(Environment.USER, System.getenv("DB_USER"));
                props.put(Environment.PASS, System.getenv("DB_PASSWORD"));

//                props.put(Environment.URL, System.getenv("dburl"));
//                props.put(Environment.USER, System.getenv("dbuser"));
//                props.put(Environment.PASS, System.getenv("dbpassword"));
                props.put(Environment.SHOW_SQL, "false");

                configuration.setProperties(props);

                //mapping classes
                configuration.addAnnotatedClass(Song.class);
                configuration.addAnnotatedClass(Artist.class);
                configuration.addAnnotatedClass(Genre.class);

                //registry
                registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();


                // create sessionFactory
                sessionFactory = configuration.buildSessionFactory(registry);
            }
            catch (Exception e){
                e.printStackTrace();
                shutdown();
            }
        }
        return sessionFactory;
    }

    public static void shutdown(){
        if(registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
