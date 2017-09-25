package Travel;

import annotations.Component;
import annotations.PostConstruct;
import annotations.PreDestroy;
import annotations.Scope;
import enums.ScopeEnum;

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
