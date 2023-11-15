package org.example.client.view;

import org.example.client.view.GameWindow;
import org.example.client.model.GameModel;

public class GameView implements Runnable {
    GameModel gameModel;
    private Thread renderThread;
    private TennisCourtView tennisCourtView;
    private GameWindow gameWindow;
    private GamePanelView gamePanel;
//    private final int FPS_SET = 120;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        gamePanel = new GamePanelView(this);
        gameWindow = new GameWindow(gamePanel);
        tennisCourtView = new TennisCourtView(this);
        gamePanel.requestFocus();
        startRenderLoop();
    }

    private void startRenderLoop() {
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {
        while (true) {
//            if (game.getShouldRepaint())
            gamePanel.repaint();
        }

    }

    public GameWindow getGameWindow(){
        return gameWindow;
    }

    public GameModel getGameModel(){
        return gameModel;
    }
    public TennisCourtView getTennisCourtView(){return tennisCourtView;}
}
