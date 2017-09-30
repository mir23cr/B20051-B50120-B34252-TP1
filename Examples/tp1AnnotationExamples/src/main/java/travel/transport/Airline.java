package travel.transport;

import cr.ac.ucr.ecci.eternaPrimavera.annotations.Component;
import cr.ac.ucr.ecci.eternaPrimavera.annotations.PostConstruct;
import cr.ac.ucr.ecci.eternaPrimavera.annotations.PreDestroy;
import cr.ac.ucr.ecci.eternaPrimavera.annotations.Scope;
import cr.ac.ucr.ecci.eternaPrimavera.enums.ScopeEnum;

@Component("airline")
@Scope(ScopeEnum.SINGLETON)
public class Airline {
    private String name;

    public Airline() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init(){
        this.name = "American Airlines";
        System.out.println("Bienvenidos a " + this.name);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Chaito de la aerolínea");
    }
}
