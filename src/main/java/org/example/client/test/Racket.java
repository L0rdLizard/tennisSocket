package org.example.client.test;

import java.awt.*;

public class Racket {
    int x, y, w, h;
    Color color;

    public Racket(int x, int y) {
        this.x = x;
        this.y = y;

        w = 9;
        h = 90;
    }

    public void updateRacket() {

    }

    public void setRacketPos(int y) {
        this.y = y - (h / 2);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(w));
        g2d.drawLine(x, y, x, y + h);
    }

}
