package dev.Maksim.client;

import dev.Maksim.client.contoller.Controller;
import dev.Maksim.client.model.GameModel;
import dev.Maksim.client.view.GameView;

public class Main {
    public synchronized static void main(String[] args) {
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameModel);
        Controller controller = new Controller(gameView.getGamePanelView());
    }
}
