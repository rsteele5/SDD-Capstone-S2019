package gamescreens.screens.Gameplay.Level;

import gamescreens.GameScreen;
import gamescreens.ScreenManager;

public class BedroomLevel extends GameScreen implements LevelBuilder {
    private static Level level;

    public BedroomLevel(ScreenManager screenManager) {
        super(screenManager, "BedroomLevel", 1f);
    }

    public static BedroomLevel create(ScreenManager screenManager) {
        level = new Level();
        return new BedroomLevel(screenManager);
    }

    @Override
    public void buildTerrain() {
        //This is where the instruction for how to procedurally generate a level would go

    }

    @Override
    public void buildPlayer() {

    }

    @Override
    public void buildEnemies() {

    }

    @Override
    protected void initializeScreen() {
        level.setBackground("/assets/backgrounds/BG-Level.png");
        level.background.addToScreen(this, true);
    }
}
