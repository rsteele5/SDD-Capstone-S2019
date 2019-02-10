package view.renderengine.screens;

import control.ScreenManager;
import model.levels.Level;
import view.renderengine.GameScreen;

public class GameplayScreen extends GameScreen {
    Level currentLevel;

    public GameplayScreen(ScreenManager screenManager) {
        super(screenManager);
    }

    @Override
    public void updateTransitionOn() {

    }

    @Override
    public void updateTransitionOff() {

    }
}
