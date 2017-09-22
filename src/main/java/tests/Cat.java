package tests;

import annotations.Autowired;
import annotations.Component;
import annotations.Qualifier;

/**
 * @author Vladimir Aguilar
 * Creation Date: 18/9/2017
 */
@Component
public class Cat {
    private String name = "Puchina";
    private int age = 17;
    @Autowired
    public Cat(String perro) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void init(){
        System.out.println("Initializing cat...");
    }

    public void  destroy(){
        System.out.println("Destroying cat...");
    }
}
