package tests;

/**
 * @author Vladimir Aguilar
 * @author Mariana Abellan
 * @author David González
 * Creation Date: 18/9/2017
 */
public class Dog {
    private String name = "Pili";
    private House choza;

    public Dog(House choza) {
        this.choza = choza;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(){
        System.out.println("Initializing dog...");
    }

    public void  destroy(){
        System.out.println("Destroying dog...");
    }
}
