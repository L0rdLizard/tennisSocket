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
        this.x += xDir;
        this.y += yDir;

//        if (((x+w) < tennisCourtModel.getRacketLeft().getX()) &&
//                ((y + h) < (tennisCourtModel.getRacketLeft().getY() + tennisCourtModel.getRacketLeft().getH())) &&
//                    ((y + h) > (tennisCourtModel.getRacketLeft().getY()))){
//            xDir *= -1;
//            color = newColor();
//        }
        if ((x+w) < tennisCourtModel.getRacketLeft().getX() + w)
            if ((y + h) < (tennisCourtModel.getRacketLeft().getY() + tennisCourtModel.getRacketLeft().getH()))
                if ((y + h) > (tennisCourtModel.getRacketLeft().getY()))
                    if (xDir < 0)
                        if ((x+w) - tennisCourtModel.getRacketLeft().getX() >= xDir){
                            System.out.println("Left");
                            xDir *= -1;
                            color = newColor();
                        }

        if ((x+w) > tennisCourtModel.getRacketRight().getX())
            if ((y + h) < (tennisCourtModel.getRacketRight().getY() + tennisCourtModel.getRacketRight().getH()))
                if ((y + h) > (tennisCourtModel.getRacketRight().getY()))
                    if (xDir > 0)
                        if ((x+w) - tennisCourtModel.getRacketRight().getX() <= xDir){
                            System.out.println("Right");
                            xDir *= -1;
                            color = newColor();
                        }

        if ((x + w) > WindowWidth - 15 || x < 0) {
            xDir *= -1;
            color = newColor();
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
    public Color getColor(){
        return color;
    }

}