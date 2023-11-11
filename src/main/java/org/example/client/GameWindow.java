package org.example.client;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;
    private int width = 800;
    private int height = 600;

    public GameWindow(GamePanel gamePanel) {

        jframe = new JFrame();

        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

}