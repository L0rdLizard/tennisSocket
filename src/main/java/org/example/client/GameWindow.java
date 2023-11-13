package org.example.client;

import javax.swing.JFrame;
import static org.example.client.Constants.*;

public class GameWindow {
    private JFrame jframe;
    private int width = WindowWidth;
    private int height = WindowHeight;

    public GameWindow(GamePanel gamePanel) {

        jframe = new JFrame();

        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

}