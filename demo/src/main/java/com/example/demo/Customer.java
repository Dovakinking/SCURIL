package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long identificator;
    @Column(name = "username")
    private String username;
    @Column(name = "age")
    private Integer age;
    @Column(name = "id")
    private long id;
    @Column(name = "password")
    private String password;

    Customer(){}
    public Customer(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
    public long getIdentificator() {
        return identificator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
