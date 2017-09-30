package travel.places;

import cr.ac.ucr.ecci.eternaPrimavera.annotations.*;
import travel.transport.Airline;

@Component("city")
@Lazy
public class City {
    private String name;
    private Country country;
    private Airline airline;

    @Autowired
    public City(@Qualifier("country") Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Airline getAirline() {
        return airline;
    }

    @Autowired
    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    @PostConstruct
    public void beginCity(){
        this.name = "Chepe";
        System.out.println("Welcome to: " + this.name + " in the country: " + this.country.getName());
    }

    @PreDestroy
    public void destroyCity(){
        System.out.println("All the citizens are dying...");
    }
}
