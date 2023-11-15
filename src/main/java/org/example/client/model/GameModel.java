package org.example.client.model;

public class GameModel implements Runnable{
    TennisCourtModel tennisCourtModel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public GameModel() {
        initClasses();
        startGameLoop();
    }

    private void initClasses() {
        tennisCourtModel = new TennisCourtModel();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void updates(){
        for (BallModel ball : tennisCourtModel.getBalls()){
            ball.updateBall();
        }
    }


    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                updates();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
//                shouldRepaint = false;
                frames++;
                deltaF--;
            }
//            else shouldRepaint = true;

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }


//    @Override
//    public void run() {
//
//        double timePerFrame = 1000000000.0 / FPS_SET;
//        long lastFrame = System.nanoTime();
//        long now = System.nanoTime();
//
//        int frames = 0;
//        long lastCheck = System.currentTimeMillis();
//
//        while (true) {
//
//            now = System.nanoTime();
//            if (now - lastFrame >= timePerFrame) {
//                gamePanel.repaint();
//                lastFrame = now;
//                frames++;
//            }
//
//            if (System.currentTimeMillis() - lastCheck >= 1000) {
//                lastCheck = System.currentTimeMillis();
////                System.out.println("FPS: " + frames);
//                frames = 0;
//            }
//        }
//
//    }

    public TennisCourtModel getTennisCourt(){
        return tennisCourtModel;
    }
}
