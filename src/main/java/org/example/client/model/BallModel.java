package org.example.client.model;

import java.awt.*;
import java.util.Random;
import static org.example.client.test.Constants.*;

public class BallModel {
    private int x, y, w, h;
    private int xDir = 4, yDir = 2;
    private Color color;
    private Random random;

    public BallModel(int x, int y) {
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