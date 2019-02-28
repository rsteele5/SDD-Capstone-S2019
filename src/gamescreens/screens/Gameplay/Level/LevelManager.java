package gamescreens.screens.Gameplay.Level;

import gamescreens.GameScreen;
import gamescreens.ScreenManager;

public class LevelManager extends GameScreen {
    private static LevelBuilder lBuild;

    public LevelManager(ScreenManager screenManager) {
        super(screenManager, "LevelManager", 1f);
    }

    public static LevelManager create(ScreenManager screenManager, LevelBuilder levelBuild) {
        lBuild = levelBuild;
        return new LevelManager(screenManager);
    }

    @Override
    protected void initializeScreen() {
        lBuild.buildBackground().addToScreen(this, true);
        lBuild.buildTerrain(this);
        lBuild.buildPlayer(this);
    }
}
