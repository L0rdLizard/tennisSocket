package org.example.client.model;

import org.example.client.Ball;
import org.example.client.Racket;

import java.util.ArrayList;

public class TennisCourt {
    private ArrayList<Ball> balls = new ArrayList<>();
    private Racket racketLeft;
    private Racket racketRight;
    public TennisCourt(){
        spawnBall(250, 80);

        racketLeft = new Racket(30, 150);
        racketRight = new Racket(754, 150);
    }

    public void setRacketPos(int y) {
        this.racketLeft.setRacketPos(y);
    }


    public void spawnBall(int x, int y) {
        balls.add(new Ball(x, y));
    }

    public ArrayList<Ball> getBalls(){
        return balls;
    }

    public Racket getRacketLeft(){
        return racketLeft;
    }

    public Racket getRacketRight(){
        return racketRight;
    }
}
