package model;//package model;

public abstract class Entity {
    // For polymorphism purpose

    private int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

}