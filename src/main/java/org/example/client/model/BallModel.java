package org.example.client.model;

import java.awt.*;
import java.util.Random;
import static org.example.client.test.Constants.*;

public class BallModel {
    private TennisCourtModel tennisCourtModel;
    private int x, y, w, h;
    private int xDir = 2, yDir = 1;
    private Color color;
    private Random random;
    private boolean collider = false;

    public BallModel(TennisCourtModel tennisCourtModel, int x, int y) {
        this.tennisCourtModel = tennisCourtModel;
        this.x = x;
        this.y = y;
        random = new Random();
        w = 30;
        h = w;
        color = newColor();
    }

    public void updateBall() {
//        if (collider) {return;}
        this.x += xDir;
        this.y += yDir;


        if ((x+w) < tennisCourtModel.getRacketLeft().getX() + w)
            if ((y + h) <= (tennisCourtModel.getRacketLeft().getY() + tennisCourtModel.getRacketLeft().getH() + 15))
                if ((y + h) > (tennisCourtModel.getRacketLeft().getY()))
                    if (xDir < 0)
                        if ((x+w) - tennisCourtModel.getRacketLeft().getX() >= xDir){
                            xDir *= -1;
                            color = newColor();
//                            stop();
//                            tennisCourtModel.getGameModel().stopT();
                        }

        if ((x+w) > tennisCourtModel.getRacketRight().getX())
            if ((y + h) <= (tennisCourtModel.getRacketRight().getY() + tennisCourtModel.getRacketRight().getH() + 15))
                if ((y + h) > (tennisCourtModel.getRacketRight().getY()))
                    if (xDir > 0)
                        if ((x+w) - tennisCourtModel.getRacketRight().getX() <= xDir){
                            xDir *= -1;
                            color = newColor();
                        }

        if ((x + w) > WindowWidth - 15 || x < 0) {
            xDir *= -1;
            color = newColor();
            if (x < 0){
                tennisCourtModel.plusScoreRight();
            } else {
                tennisCourtModel.plusScoreLeft();
            }
//            collider = true;
        }
        if ((y + h) > WindowHeight - 45 || y < 0) {
            yDir *= -1;
            color = newColor();
        }

    }

    private Color newColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getW(){
        return w;
    }

    public int getH(){
        return h;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public Color getColor(){
        return color;
    }
    public boolean isCollider(){
        return collider;
    }
    public void resetCollider(){
        collider = false;
    }
    public void stopGame(){
        xDir = 0;
        yDir = 0;
    }

    public void continueGame(){
        xDir = 2;
        yDir = 1;
    }

    public void setxDir(int d){
        xDir = d;
    }

}