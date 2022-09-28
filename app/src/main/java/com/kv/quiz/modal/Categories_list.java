package com.kv.quiz.modal;

public class Categories_list {
    String id,name,question, time;

    public Categories_list(String id, String question, String time,String name) {
        this.id = id;
        this.question = question;
        this.time = time;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}