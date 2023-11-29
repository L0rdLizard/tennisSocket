package org.example.client.view;

import org.example.client.model.GameModel;

public class GameView implements Runnable {
    GameModel gameModel;
    private Thread renderThread;
    private TennisCourtView tennisCourtView;
    private GameWindow gameWindow;
    private GamePanelView gamePanelView;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        gamePanelView = new GamePanelView(this);
        gameWindow = new GameWindow(gamePanelView);
        tennisCourtView = new TennisCourtView(this);
        gamePanelView.requestFocus();
        startRenderLoop();
    }

    private void startRenderLoop() {
        renderThread = new Thread(this);
        renderThread.start();
    }

//    @Override
//    public void run() {
//        if (gameModel.isPlaying()) {
//            while (true) {
//                gamePanel.repaint();
//            }
//        }
//    }

    @Override
    public void run() {
        while (true) {
            gamePanelView.repaint();
        }
    }

    public GameWindow getGameWindow(){
        return gameWindow;
    }

    public GameModel getGameModel(){
        return gameModel;
    }
    public TennisCourtView getTennisCourtView(){return tennisCourtView;}
    public GamePanelView getGamePanelView() {
        return gamePanelView;
    }
}
