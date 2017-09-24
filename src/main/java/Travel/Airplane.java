package Travel;

import annotations.*;

@Component("airplane")
@Scope("prototype")
public class Airplane {
    private String id;
    private Airline airline;
    private Flight flight;

    @Autowired
    public Airplane( Airline airline, Flight flight) {
        this.id = "5555";
        this.airline = airline;
        this.flight = flight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airline getAirplane() {
        return airline;
    }

    public void Airline(Airline airplane) {
        this.airline = airplane;
    }

    @PostConstruct
    public void start(){
        System.out.println("Initializing airplane...");
    }
}
