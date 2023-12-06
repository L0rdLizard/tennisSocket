package org.example.client.model;

import org.example.client.test.Ball;

import java.util.ArrayList;
import java.util.Random;

public class TennisCourtModel {
    private ArrayList<BallModel> balls = new ArrayList<>();
    private GameModel gameModel;
    private RacketModel racketLeft;
    private RacketModel racketRight;
    private int scoreLeft = 0;
    private int scoreRight = 0;
    private int winSide = 0;
    Random random = new Random();

    public TennisCourtModel(GameModel gameModel){
        this.gameModel = gameModel;
        spawnBall(377, 260);
//        spawnBall(250, 80);

        racketLeft = new RacketModel(50, 150);
        racketRight = new RacketModel(734, 150);
    }

    public void setRacketPosLeft(int y) {
        this.racketLeft.setRacketPos(y);
    }
    public void setRacketPosRight(int y) {
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
        scoreRight = 0;
        scoreLeft = 0;
        this.winSide = winSide;
        if (gameModel.isPlaying()){
            gameModel.changePlaying(false);
        }
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
        respawnBalls();
        scoreLeft++;
        gameModel.stopT();
    }

    public void plusScoreRight(){
        respawnBalls();
        scoreRight++;
        gameModel.stopT();
    }

    public void respawnBalls(){
        for (BallModel ball : balls){
            int randomInt = random.nextInt(2);
            ball.setX(377);
            ball.setY(260);
        }
    }

    public int getScoreLeft(){
        return scoreLeft;
    }

    public int getScoreRight(){
        return scoreRight;
    }
}
