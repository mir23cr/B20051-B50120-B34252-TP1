package tests;

import annotations.*;

/**
 * @author Vladimir Aguilar
 * Creation Date: 18/9/2017
 */
@Component("puchin")
@Scope("prototype")
public class Cat {
    private String name = "Puchina";
    private int age = 17;
    private Person owner;

    public Cat(){
    }

    @Autowired
    public Cat(@Qualifier("daddy")Person person) {
        this.owner = person;
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

    @PostConstruct
    public void init(){
        System.out.println("Initializing cat...");
    }

    @PreDestroy
    public void  destroy(){
        System.out.println("Destroying cat...");
    }
}
