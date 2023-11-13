package org.example.client;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import org.example.client.inputs.KeyboardInputs;
import org.example.client.inputs.MouseInputs;
import static org.example.client.Constants.*;

public class GamePanel extends JPanel {
    public Game game;
    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
//    private float xDelta = 100, yDelta = 100;
//    private float xDir = 1f, yDir = 1f;
    private Color color = new Color(150, 20, 90);
    private Random random;

    private int WWidth = WindowWidth;
    private int WHeight = WindowHeight;

    private ArrayList<MyRect> ovals = new ArrayList<>();

    public GamePanel(Game game) {

        random = new Random();

        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);

//        addKeyListener(new KeyboardInputs(this));
//        addMouseListener(mouseInputs);
//        addMouseMotionListener(mouseInputs);

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        spawnRect(150, 150);
    }

//    public void changeXDelta(int value) {
//        this.xDelta += value;
//    }
//
//    public void changeYDelta(int value) {
//        this.yDelta += value;
//    }

//    public void setRectPos(int x, int y) {
//        this.xDelta = x;
//        this.yDelta = y;
//    }

    public void spawnRect(int x, int y) {
        ovals.add(new MyRect(x, y));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (MyRect rect : ovals) {
            rect.updateRect();
            rect.draw(g);
        }

//        updateRectangle();

        drawField(g);

        g.setColor(color);
//        g.fillRect((int) xDelta, (int) yDelta, 200, 50);
    }

    public void drawField(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float[] dash = { 10.0f };
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }
    public Game getGame(){
        return game;
    }

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        for (MyRect rect : ovals) {
//            rect.updateRect();
//            rect.draw(g);
//        }
//
//
//        g.setColor(color);
//    }

//    private void updateRectangle() {
//        xDelta += xDir;
//        if (xDelta > 400 || xDelta < 0) {
//            xDir *= -1;
//            color = getRndColor();
//        }
//
//        yDelta += yDir;
//        if (yDelta > 400 || yDelta < 0) {
//            yDir *= -1;
//            color = getRndColor();
//        }
//    }

//    private Color getRndColor() {
//        int r = random.nextInt(255);
//        int g = random.nextInt(255);
//        int b = random.nextInt(255);
//
//        return new Color(r, g, b);
//    }


    // Temp
    public class MyRect {
        int x, y, w, h;
        int xDir = 1, yDir = 1;
        Color color;

        public MyRect(int x, int y) {
            this.x = x;
            this.y = y;
//            w = random.nextInt(50);
            w = 30;
            h = w;
            color = newColor();
        }

        public void updateRect() {
            this.x += xDir;
            this.y += yDir;

            if ((x + w) > WWidth - 15 || x < 0) {
                xDir *= -1;
                color = newColor();
            }
            if ((y + h) > WHeight - 35 || y < 0) {
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

}