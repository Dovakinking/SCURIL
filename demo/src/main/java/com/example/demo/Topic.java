package com.example.demo;

import java.util.ArrayList;

public class Topic {
    String name;
    ArrayList<String> comment;

    User user;

    public Topic()
    {
    }

    public Topic(String name, ArrayList<String> comment, User user)
    {
        this.name = name;
        this.comment = comment;
        this.user = user;
        Topic top = new Topic();
        top.setName(name);
        top.comment = comment;
        user.comments.add(top);
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public void addComment(ArrayList<String> comment) {
        this.comment.addAll(comment);
    }

    public void setComment(ArrayList<String> comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

