package org.example.client.test;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.example.client.test.inputs.KeyboardInputs;
import org.example.client.test.inputs.MouseInputs;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    private Color color = new Color(150, 20, 90);

    private ArrayList<Ball> balls = new ArrayList<>();

    private Racket racketLeft;
    private Racket racketRight;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        spawnBall(250, 80);
//        spawnRacket(30, 150);
        racketLeft = new Racket(30, 150);
        racketRight = new Racket(754, 150);
    }

    public void setRacketPos(int y) {
        this.racketLeft.setRacketPos(y);
    }


    public void spawnBall(int x, int y) {
        balls.add(new Ball(x, y));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball ball : balls) {
            ball.updateRect();
            ball.draw(g);
        }

        racketLeft.updateRacket();
        racketLeft.draw(g);

        racketRight.updateRacket();
        racketRight.draw(g);

        drawField(g);

        g.setColor(color);
    }

    public void drawField(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float[] dash = { 10.0f };
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }

}