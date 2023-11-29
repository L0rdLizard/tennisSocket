package org.example.client;

import org.example.client.contoller.Controller;
import org.example.client.model.GameModel;
import org.example.client.view.GameView;

public class Main {
    public synchronized static void main(String[] args) {
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameModel);
        Controller controller = new Controller(gameView.getGamePanelView());
    }
}
