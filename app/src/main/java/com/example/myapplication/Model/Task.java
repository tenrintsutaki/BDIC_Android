package com.example.myapplication.Model;

public class Task {
    private int id;
    private String name;
    private String content;
    private int isFinished;
    private int award;

    public Task(int id, String name, String content, int isFinished, int award) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.isFinished = isFinished;
        this.award = award;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFinished() {
        return isFinished;
    }

    public void setFinished(int finished) {
        isFinished = finished;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
