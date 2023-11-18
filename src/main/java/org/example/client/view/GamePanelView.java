package org.example.client.view;

import org.example.client.inputs.KeyboardInputs;
import org.example.client.inputs.MouseInputs;
import org.example.client.model.BallModel;
import org.example.client.model.RacketModel;

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
        RacketModel racketLeft = gameView.getGameModel().getTennisCourt().getRacketLeft();
        RacketModel racketRight = gameView.getGameModel().getTennisCourt().getRacketRight();

        for (BallModel ball : gameView.getGameModel().getTennisCourt().getBalls()) {
            gameView.getTennisCourtView().drawBall(g, ball.getColor(), ball.getX(), ball.getY(), ball.getW(), ball.getH());
        }

        gameView.getTennisCourtView().drawRacket(g, racketLeft.getX(), racketLeft.getY(), racketLeft.getW(), racketLeft.getH());

        gameView.getTennisCourtView().drawRacket(g, racketRight.getX(), racketRight.getY(), racketRight.getW(), racketRight.getH());

        drawField(g);

        g.setColor(color);
    }

    public void drawField(Graphics g){
        setBackground(Color.DARK_GRAY);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float[] dash = { 10.0f };
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2d.setColor(Color.GRAY);
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }

    public GameView getGameView() {
        return gameView;
    }
}
