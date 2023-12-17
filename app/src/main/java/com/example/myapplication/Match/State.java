package com.example.myapplication.Match;

public class State {
    private volatile boolean isMatched;//In order to let two threads to share a single var, volatile is necessary.

    State(){
        isMatched = false;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public boolean getMatched() {
        return isMatched;
    }

}
