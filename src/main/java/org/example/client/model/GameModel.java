package org.example.client.model;

public class GameModel {
    TennisCourt tennisCourt;

    public GameModel() {
        initClasses();
    }

    private void initClasses() {
        tennisCourt = new TennisCourt();
    }

    public TennisCourt getTennisCourt(){
        return tennisCourt;
    }
}
