package com.example.myapplication.Model;

public class Running {
    private int id;
    private int user_id;
    private double miles;
    private double time;
    private double speed;
    private double energy;

    public Running(int id, int user_id, double miles, double time, double speed, double energy) {
        this.id = id;
        this.user_id = user_id;
        this.miles = miles;
        this.time = time;
        this.speed = speed;
        this.energy = energy;
    }

    public Running(int user_id, double miles, double time, double speed, double energy) {
        this.user_id = user_id;
        this.miles = miles;
        this.time = time;
        this.speed = speed;
        this.energy = energy;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public double getMiles() {
        return miles;
    }

    public double getTime() {
        return time;
    }

    public double getSpeed() {
        return speed;
    }

    public double getEnergy() {
        return energy;
    }
}
