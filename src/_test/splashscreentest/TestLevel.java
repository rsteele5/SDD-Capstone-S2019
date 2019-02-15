package _test.splashscreentest;

import control.ScreenManager;
import model.levels.Level;
import model.levels.LevelData;
import utilities.Debug;
import utilities.DebugEnabler;

import java.awt.*;

public class TestLevel extends Level {

    private final String name = "TestLevel";

    public TestLevel(){
        this.levelData = new TestLevelData();
    }

    public TestLevel(LevelData levelData){
        super(levelData);
    }

    @Override
    public void update() {
        levelData.update();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Debug.success(DebugEnabler.RENDERABLE_LOG, name+" Draw");
        levelData.draw(graphics2D);
    }

    @Override
    public void loadObjects(ScreenManager screenManager) {
        levelData.loadObjects(screenManager);
    }

    @Override
    public int getLoadData() {
        //TODO: This should be automated somehow
        return levelData.getLoadData();
    }

    @Override
    public LevelData getLevelData() {
        return null;
    }
}
