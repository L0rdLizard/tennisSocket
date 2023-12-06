package dev.Maksim.client.model;

import java.awt.*;

public class RacketModel {
    private int x, y, w, h;
    Color color;

    public RacketModel(int x, int y) {
        this.x = x;
        this.y = y;

        this.w = 9;
        this.h = 90;
    }

    public void setRacketPos(int y) {
        this.y = y - (h / 2);
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
