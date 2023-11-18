package org.example.client.model;

public class GameModel implements Runnable{
    TennisCourtModel tennisCourtModel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private boolean playing = true;

    public GameModel() {
        initClasses();
        startGameLoop();
    }

    private void initClasses() {
        tennisCourtModel = new TennisCourtModel(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
//        this.start();
    }

    private void updates(){
        if (playing) {
            for (BallModel ball : tennisCourtModel.getBalls()) {
                ball.updateBall();
            }
        }
    }

    public void stopT(){
        try {
            changePlaying();
            Thread.sleep(2000);
            changePlaying();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }


    public TennisCourtModel getTennisCourt(){
        return tennisCourtModel;
    }

    public void changePlaying(){
        if (playing){
            playing = false;
        } else{
            playing = true;
        }
    }

    public boolean isPlaying(){
        return playing;
    }
}
