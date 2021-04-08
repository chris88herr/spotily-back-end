package com.chrisProjects.lyricsScraper.Utils;

import com.sun.xml.bind.api.impl.NameConverter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.concurrent.ExecutionException;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory==null){
            try{
                //registry
                registry = new StandardServiceRegistryBuilder().configure().build();

                //create Metadatasources
                MetadataSources sources = new MetadataSources(registry);

                //create metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // create sessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
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
