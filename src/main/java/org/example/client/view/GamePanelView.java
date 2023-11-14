package org.example.client.view;

import org.example.client.Ball;
import org.example.client.inputs.KeyboardInputs;
import org.example.client.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanelView extends JPanel {
    GameView gameView;
    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    private Color color = new Color(150, 20, 90);

    public GamePanelView(GameView gameView) {
        this.gameView = gameView;
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball ball : gameView.getGameModel().getTennisCourt().getBalls()) {
            ball.updateRect();
            ball.draw(g);
        }

//        gameView.getGameModel().getTennisCourt().getRacketLeft().updateRacket();
        racketLeft.draw(g);

//        gameView.getGameModel().getTennisCourt().getRacketRight().updateRacket();
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
