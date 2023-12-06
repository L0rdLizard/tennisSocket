package dev.Maksim.client.contoller;

import dev.Maksim.client.view.GamePanelView;


public class Controller{
    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    GamePanelView gamePanelView;
//    private ClientUDP clientUDP;

    public Controller(GamePanelView gamePanelView) {
        this.gamePanelView = gamePanelView;
        this.mouseInputs = new MouseInputs(gamePanelView);
        this.keyboardInputs = new KeyboardInputs(gamePanelView);

        gamePanelView.addMouseListener(mouseInputs);
        gamePanelView.addKeyListener(keyboardInputs);
        gamePanelView.addMouseMotionListener(mouseInputs);

//        this.clientUDP = new ClientUDP(gamePanelView.getGameView().getGameModel().getTennisCourt());
    }
}
