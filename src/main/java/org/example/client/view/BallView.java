package org.example.client.view;

import org.example.client.model.BallModel;

import java.awt.*;

public class BallView {
    private BallModel ballModel;

    public BallView(){

    }
    public void draw(Graphics g, Color color, int x, int y, int w, int h) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }
}
