package org.example.client.view;

import java.awt.*;

public class TennisCourtView {
    private GameView gameView;

    public TennisCourtView(GameView gameView){
        this.gameView = gameView;
    }

    public void drawBall(Graphics g, Color color, int x, int y, int w, int h) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }

    public void drawRacket(Graphics g, int x, int y, int w, int h) {
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(w));
        g2d.drawLine(x, y, x, y + h);
    }

}
