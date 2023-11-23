package org.example.client.model;

public class GameModel implements Runnable{
    TennisCourtModel tennisCourtModel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private boolean playing = true;
    private long lastCheck = 0;
    private long previousTime = 0;


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
        tennisCourtModel.update();
    }

    public void stopT(){
        try {
            changePlaying();
            Thread.sleep(2000);
            lastCheck = System.currentTimeMillis();
            previousTime = System.nanoTime();
            changePlaying();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
//            updates();
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
        System.out.println(playing);
    }

    public boolean isPlaying(){
        return playing;
    }
}
