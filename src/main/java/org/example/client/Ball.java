package org.example.client;

import java.awt.*;
import java.util.Random;
import static org.example.client.Constants.*;

public class Ball {
    int x, y, w, h;
    int xDir = 4, yDir = 2;
    Color color;
    private Random random;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        random = new Random();
//            w = random.nextInt(50);
        w = 30;
        h = w;
        color = newColor();
    }

    public void updateRect() {
        this.x += xDir;
        this.y += yDir;

        if ((x + w) > WindowWidth - 15 || x < 0) {
            xDir *= -1;
            color = newColor();
        }
        if ((y + h) > WindowHeight - 35 || y < 0) {
            yDir *= -1;
            color = newColor();
        }
    }

    private Color newColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public void draw(Graphics g) {
        g.setColor(color);
//            g.fillRect(x, y, w, h);
        g.fillOval(x, y, w, h);
    }

}
