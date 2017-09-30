package family;

/**
 * @author Vladimir Aguilar
 */
public class Person {
    private String name = "Juan";

    public Person(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(){
        System.out.println("Initializing person...");
    }

    public void  destroy(){
        System.out.println("Destroying person...");
    }
}
