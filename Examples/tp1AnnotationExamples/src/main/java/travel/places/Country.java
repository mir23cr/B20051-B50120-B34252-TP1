package travel.places;

import cr.ac.ucr.ecci.eternaPrimavera.annotations.Component;


/**
 * Created by jmmesenc on 9/23/2017.
 */
@Component("country")
public class Country {
    private String name;


    public Country() {
        this.name = "Costa Rica";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
