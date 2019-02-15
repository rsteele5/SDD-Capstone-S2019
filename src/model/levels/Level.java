package model.levels;

import control.ScreenManager;

import java.awt.*;

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

    public abstract LevelData getLevelData();
}
