package main;

import cr.ac.ucr.ecci.eternaPrimavera.context.ApplicationContext;
import cr.ac.ucr.ecci.eternaPrimavera.context.XmlApplicationContext;
import family.House;
import family.pets.Cat;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new XmlApplicationContext("beans.xml");

        /*If you comment this, only one house is going to appear.*/
        House lacho = applicationContext.getBean(House.class,"lacho");
        House home =  (House) applicationContext.getBean("home");
        Cat cat = applicationContext.getBean(Cat.class,"puchin");
        System.out.println("The name of the cat is: " + cat.getName());

        applicationContext.close();
    }
}
