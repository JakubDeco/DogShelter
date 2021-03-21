package sk.kosickaakademia.entity;

import sk.kosickaakademia.enumerator.Sex;

import java.util.Date;

public class Dog {
    private int id;
    private String name;
    private int age;
    private Sex sex;
    private String breed;
    private String color;
    private Date arrival;

    public Dog(String name, int age, int sex, String breed, String color, Date arrival) {
        this.name = name;
        this.age = age;
        this.sex = sex==0 ? Sex.MALE : Sex.FEMALE;
        this.breed = breed;
        this.color = color;
        this.arrival = arrival;
    }

    public Dog(int id, String name, int age, int sex, String breed, String color, Date arrival) {
        this(name, age, sex, breed, color, arrival);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }

    public Date getArrival() {
        return arrival;
    }
}
