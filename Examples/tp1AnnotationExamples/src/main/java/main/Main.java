package main;

import cr.ac.ucr.ecci.eternaPrimavera.context.AnnotationApplicationContext;
import cr.ac.ucr.ecci.eternaPrimavera.context.ApplicationContext;
import travel.places.City;
import travel.places.Country;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationApplicationContext("travel");
        Country country = applicationContext.getBean(Country.class,"country");
        System.out.println("Best country: " + country.getName());
        /*Comment the next line to check lazy loading*/
        City city = applicationContext.getBean(City.class,"city");
        System.out.println(city.getAirline().getName());
        applicationContext.close();

    }
}
