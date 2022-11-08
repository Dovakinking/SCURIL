package com.example.demo;

public class User
{
    String name;
    int age;

    public User()
    {
        this.age = 17;
        this.name = "Vlad";
    }
    public User(String name, int age)
    {
        this.age = age;
        this.name = name;
    }

    public User(String name)
    {
        this.age = Integer.parseInt(name.split("&")[1]);
        this.name = name.split("&")[0];
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "Пользователь по имени " + name + " " + age + (age % 10 == 1 ? " год": age % 10 == 0 ? " лет" : age % 10 <= 4 ? " года" : " лет");
    }
}

