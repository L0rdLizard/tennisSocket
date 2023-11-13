package org.example.client;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import static org.example.client.Constants.*;

public class GameWindow {
    private JFrame jframe;
    private int width = WindowWidth;
    private int height = WindowHeight;

//    public GameWindow(GamePanel gamePanel) {
//
//        jframe = new JFrame();
//
//        jframe.setSize(width, height);
//        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jframe.add(gamePanel);
//        jframe.setLocationRelativeTo(null);
//        jframe.setVisible(true);
//    }

    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
//        jFrame.setLocation(0, 0);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
//        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setSize(width, height);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
    }

}