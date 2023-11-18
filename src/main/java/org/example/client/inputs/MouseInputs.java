package org.example.client.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.example.client.test.GamePanel;
import org.example.client.view.GamePanelView;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanelView gamePanel;

    public MouseInputs(GamePanelView gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//		gamePanel.setRacketPos(e.getY());
        gamePanel.getGameView().getGameModel().getTennisCourt().setRacketPos(e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//		System.out.println("Mouse clicked!");
//        gamePanel.spawnBall(e.getX(),e.getY());
        gamePanel.getGameView().getGameModel().changePlaying();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}