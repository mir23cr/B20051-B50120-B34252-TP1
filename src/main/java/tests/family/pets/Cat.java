package tests.family.pets;

import annotations.*;
import enums.ScopeEnum;
import tests.family.Person;

/**
 * @author Vladimir Aguilar
 * Creation Date: 18/9/2017
 */
@Component("puchin")
@Scope(ScopeEnum.PROTOTYPE)
public class Cat {
    private String name = "Puchina";
    private int age = 17;
    private Person owner;
    private Dog friend;


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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Dog getFriend() {
        return friend;
    }

    @Autowired
    public void setFriend(Dog friend) {
        this.friend = friend;
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
