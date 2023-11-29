package org.example.client.contoller;

import org.example.client.view.GamePanelView;


public class Controller{
    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    GamePanelView gamePanelView;

    public Controller(GamePanelView gamePanelView) {
        this.gamePanelView = gamePanelView;
        this.mouseInputs = new MouseInputs(gamePanelView);
        this.keyboardInputs = new KeyboardInputs(gamePanelView);

        gamePanelView.addMouseListener(mouseInputs);
        gamePanelView.addKeyListener(keyboardInputs);
        gamePanelView.addMouseMotionListener(mouseInputs);
    }


}
