package com.booy.listviewwithtextviewapp.Entites;

/**
 * Created by booy on 15/03/2017.
 */

public class Person {

    private int id;
    private String name;
    private int age;
    private int nbParticipations;
    private boolean selected;


    public Person() {
    }

    public Person(int id, String name, int age, int nbParticipations) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.nbParticipations = nbParticipations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNbParticipations() {
        return nbParticipations;
    }

    public void setNbParticipations(int nbParticipations) {
        this.nbParticipations = nbParticipations;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", nbParticipations=" + nbParticipations +
                '}';
    }
}
