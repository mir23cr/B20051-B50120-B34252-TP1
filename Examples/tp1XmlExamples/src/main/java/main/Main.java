package main;

import cr.ac.ucr.ecci.eternaPrimavera.context.ApplicationContext;
import cr.ac.ucr.ecci.eternaPrimavera.context.XmlApplicationContext;
import family.House;
import family.pets.Cat;

public class Main {
    public static void main(String[] args) {
        ApplicationContext xmlApplicationContext = new XmlApplicationContext("beans.xml");
        /*Singleton example*/
        House home = xmlApplicationContext.getBean(House.class,"home");
        System.out.println("The name of the dad is: " + home.getDad().getName());
        home.getDad().setName("Raúl");
        House housie = xmlApplicationContext.getBean(House.class,"home");
        System.out.println("The name of the dad is: " + housie.getDad().getName());

        /*Prototype example*/
        Cat killer = (Cat) xmlApplicationContext.getBean("puchin");
        System.out.println("The name of the cat is: " + killer.getName());
        killer.setName("Berlioz");
        System.out.println("The new name of the cat is: " + killer.getName());
        Cat savior = xmlApplicationContext.getBean(Cat.class,"puchin");
        System.out.println("The name of the savior is: " + savior.getName());

        xmlApplicationContext.close();
    }
}
