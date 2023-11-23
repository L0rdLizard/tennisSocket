package org.example.client.model;

import java.util.ArrayList;

public class TennisCourtModel {
    private ArrayList<BallModel> balls = new ArrayList<>();
    private GameModel gameModel;
    private RacketModel racketLeft;
    private RacketModel racketRight;
    private int scoreLeft = 0;
    private int scoreRight = 0;
    private int winSide = 0;

    public TennisCourtModel(GameModel gameModel){
        this.gameModel = gameModel;
        spawnBall(250, 80);

        racketLeft = new RacketModel(50, 150);
        racketRight = new RacketModel(734, 150);
    }

    public void setRacketPos(int y) {
        this.racketLeft.setRacketPos(y);
        this.racketRight.setRacketPos(y);
    }

    public void update(){
        if (scoreLeft >= 3){
            gameOver(1);
        }
        if (scoreRight >= 3){
            gameOver(2);
        }
    }

    public void gameOver(int winSide){
        // 1 - left
        // 2 - right
        this.winSide = winSide;
    }


    public void spawnBall(int x, int y) {
        balls.add(new BallModel(this, x, y));
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
    public GameModel getGameModel() {return gameModel;}

    public void plusScoreLeft(){
        scoreLeft++;
    }

    public void plusScoreRight(){
        scoreRight++;
    }

    public int getScoreLeft(){
        return scoreLeft;
    }

    public int getScoreRight(){
        return scoreRight;
    }
}
