package model.levels;

import control.ScreenManager;
import model.gameobjects.RenderableObject;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Level {

    protected LevelData levelData;

    public Level(){

    }

    public Level(LevelData levelData) {
        this.levelData = levelData;
    }

    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);
    public abstract void loadObjects(ScreenManager screenManager);
    public abstract int getLoadData();

    public abstract void initializeLayers();
}
