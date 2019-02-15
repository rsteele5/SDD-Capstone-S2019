package _test.splashscreentest;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.GameObject;
import model.gameobjects.renderable.RenderableObject;
import model.gameobjects.renderable.dynamicObject.enemy.floatingeye.FloatingEye;
import model.levels.LevelData;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestLevelData extends LevelData {

    private final String name = "TestLevelData";
    private int totalImagesToLoad = 0;

    public TestLevelData() {
        initializeData();
    }

    @Override
    public void update() {
        for(GameObject gameObject: gameObjects){
            gameObject.update();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        for(RenderableObject renderable: getAllRenderables()){
            Debug.success(DebugEnabler.RENDERABLE_LOG, name+" Draw");
            renderable.draw(graphics2D);
        }
    }

    @Override
    public void loadObjects(ScreenManager screenManager) {
        initializeData();
        int objectsLoaded = 0;
        Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Content");
        for(RenderableObject renderableObject: getAllRenderables()){
            renderableObject.loadImages();
            screenManager.updateLoadingScreen(++objectsLoaded);
        }
        Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Successful");
        screenManager.setLevelData(this);
    }

    @Override
    public int getLoadData() {
        return totalImagesToLoad;
    }

    @Override
    public void initializeData() {
        Debug.log(DebugEnabler.RENDERABLE_LOG, name+" Initializing Layers");
        kinematicObjects = new CopyOnWriteArrayList<>();
        gameObjects = new CopyOnWriteArrayList<>();
        renderableLayers = new CopyOnWriteArrayList<>();
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());

        FloatingEye floatingEye = new FloatingEye(1000, 500, "/assets/gameObjects/StabbyEye.png", 1);
        gameObjects.add(floatingEye);
        kinematicObjects.add(floatingEye);
        totalImagesToLoad++;
        renderableLayers.get(1).add(floatingEye);
    }
}
