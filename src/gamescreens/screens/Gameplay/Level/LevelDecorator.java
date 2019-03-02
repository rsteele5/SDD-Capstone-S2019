package gamescreens.screens.gameplay.level;

import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;

public class LevelDecorator extends GameScreen {
    private static Level lBuild;

    private LevelDecorator(ScreenManager screenManager) {
        super(screenManager, "LevelDecorator", 1f);
    }

    public static LevelDecorator create(ScreenManager screenManager, Level levelBuild) {
        lBuild = levelBuild;
        return new LevelDecorator(screenManager);
    }

    @Override
    protected void initializeScreen() {
        lBuild.buildBackground(this);
        lBuild.buildTerrain(this);
        lBuild.buildPlayer(this);
        setCamera(new Camera(this, GameEngine.players.get(0)));
    }
}
