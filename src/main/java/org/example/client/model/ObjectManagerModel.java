package org.example.client.model;

import java.awt.*;

public class ObjectManagerModel {
    public void draw(Graphics g, Color color, int x, int y, int w, int h) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }


}
