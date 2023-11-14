package org.example.client.view;

import org.example.client.GamePanel;
import org.example.client.GameWindow;
import org.example.client.model.GameModel;

public class GameView implements Runnable {
    GameModel gameModel;
    private Thread gameThread;
    private GameWindow gameWindow;
    private GamePanelView gamePanel;
    private final int FPS_SET = 120;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        gamePanel = new GamePanelView(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {

            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }

    }

    public GameWindow getGameWindow(){
        return gameWindow;
    }

    public GameModel getGameModel(){
        return gameModel;
    }
}
