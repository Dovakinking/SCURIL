package com.example.demo;

public class CustomerPublic extends Customer
{
    String username;
    int age;
    long id;


    public CustomerPublic(Customer c) {
        super(c.getUsername(), c.getPassword(), c.getAge());
        this.id = c.getId();
    }
}
