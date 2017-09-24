package tests;

import annotations.*;

/**
 * @author Vladimir Aguilar
 * Creation Date: 18/9/2017
 */
@Component("cat1")
@Scope("prototype")
public class Cat {
    private String name = "Puchina";
    private int age = 17;
    private House home;

    public Cat(){
    }
    @Autowired
    public Cat(@Qualifier("casa")House home) {
        this.home = home;
    }

    public String getName() {
        return name;
    }

    @Autowired
    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public House getHome() {
        return home;
    }

    public void setHome(House home) {
        this.home = home;
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
