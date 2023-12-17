package com.example.myapplication.Match;

public class ClientModel {
    private boolean isMatched;
    private double[] clientLocations;
    private String username;

    public ClientModel(double[] clientLocations,String username){
        this.clientLocations = clientLocations;
        this.username = username;
        isMatched = false;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    @Override
    public String toString() {
        return username+" alt: "+clientLocations[0]+" lon: "+clientLocations[1];
    }
}
