package view.renderengine.screens;

import control.ScreenManager;
import view.renderengine.GameScreen;

import java.util.concurrent.CopyOnWriteArrayList;

public class OverworldScreen extends GameScreen {

    private CopyOnWriteArrayList<Integer> house;

    public OverworldScreen(CopyOnWriteArrayList<Integer> h, ScreenManager screenManager){
        super(screenManager);
        house = h;
    }

    @Override
    public void updateTransitionOn() {

    }

    @Override
    public void updateTransitionOff() {

    }
}
