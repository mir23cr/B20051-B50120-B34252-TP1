package Travel;

import annotations.*;
import enums.ScopeEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component("flight")
@Scope(ScopeEnum.PROTOTYPE)
public class Flight {
    private String date;
    private City from;
    private Country country;

    @Autowired
    public Flight(@Qualifier("city")City from, @Qualifier("country")Country country) {
        this.from = from;
        this.country = country;
        this.initializeDate();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    private void initializeDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = dateFormat.format(new Date());
    }

    @PostConstruct
    public void start(){
        System.out.println("Initializing flight...  Date: " + date);
    }
}
