package com.kv.quiz.modal;

public class Categoryquiz {

    String name,id;
    int image;

    public Categoryquiz(int image, String name, String id) {
        this.image = image;
        this.name = name;
        this.id = id;
    }


    public Categoryquiz() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
