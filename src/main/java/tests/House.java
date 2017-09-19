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
}
