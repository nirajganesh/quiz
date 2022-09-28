package com.kv.quiz.modal;

public class Listitem {
 String id,name,ans,select;

    public Listitem(String id, String ans, String select, String name) {
        this.id = id;
        this.name = name;
        this.ans = ans;
        this.select = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
