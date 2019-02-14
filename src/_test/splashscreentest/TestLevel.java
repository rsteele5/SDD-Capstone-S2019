package _test.splashscreentest;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.RenderableObject;
import model.levels.Level;
import model.levels.LevelData;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

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
        //TODO: Update GameObjects in level data here
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
    public void initializeLayers() {
        levelData.initializeLayers();
    }
}
