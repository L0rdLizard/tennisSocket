package org.example.client;

import org.example.client.model.GameModel;
import org.example.client.view.GameView;

public class Main {
    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameModel);
    }
}
