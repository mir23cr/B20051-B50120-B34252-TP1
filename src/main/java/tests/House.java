package tests;

/**
 * @author Vladimir Aguilar
 * Creation Date: 18/9/2017
 */
public class House {
    private Person dad;
    private Dog doggie;
    private Cat cat;

    public House(){
        //dad = new Person();
    }

    public House(Dog dog, Cat cat){
        this.doggie = dog;
        this.cat = cat;
        this.dad = new Person();
        dad.setName("Bartosz");
    }

    public House(Dog dog, Cat cat, Person dad){
        this.doggie = dog;
        this.cat = cat;
        this.dad = dad;
    }

    public Person getDad() {
        return dad;
    }

    public void setDad(Person dad) {
        this.dad = dad;
    }

    public Dog getDoggie() {
        return doggie;
    }

    public void setDoggie(Dog doggie) {
        this.doggie = doggie;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public void run(){
        System.out.println("Armando el chante.");
        this.cat = new Cat("hola");
        cat.setName("Rarito");
    }

    public void close(){
        this.cat = null;
    }
}
