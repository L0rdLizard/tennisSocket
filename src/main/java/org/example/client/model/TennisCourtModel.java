package org.example.client.model;

import org.example.client.test.Racket;

import java.util.ArrayList;

public class TennisCourtModel {
    private ArrayList<BallModel> balls = new ArrayList<>();
    private RacketModel racketLeft;
    private RacketModel racketRight;
    public TennisCourtModel(){
        spawnBall(250, 80);

        racketLeft = new RacketModel(30, 150);
        racketRight = new RacketModel(754, 150);
    }

    public void update(){

    }

    public void setRacketPos(int y) {
        this.racketLeft.setRacketPos(y);
    }


    public void spawnBall(int x, int y) {
        balls.add(new BallModel(x, y));
    }

    public ArrayList<BallModel> getBalls(){
        return balls;
    }

    public RacketModel getRacketLeft(){
        return racketLeft;
    }

    public RacketModel getRacketRight(){
        return racketRight;
    }
}
