package model.levels;

import control.RenderEngine;
import control.ScreenManager;
import control.physics.Kinematic;
import model.gameobjects.*;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class LevelData {

    protected final String name = "LevelData";
    protected Player player;
    protected CopyOnWriteArrayList<GameObject> gameObjects;
    protected CopyOnWriteArrayList<GameObject> onScreen;
    protected CopyOnWriteArrayList<Kinematic> kinematicObjects;
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<RenderableObject>> renderableLayers;
    protected CopyOnWriteArrayList<Enemy> enemies;
    protected CopyOnWriteArrayList<StaticObject> staticObjects;

    public abstract void initializeLayers();


    public CopyOnWriteArrayList<CopyOnWriteArrayList<RenderableObject>> getRenderableLayers(){
        return renderableLayers;
    }

    public CopyOnWriteArrayList<RenderableObject> getAllRenderables() {
        CopyOnWriteArrayList<RenderableObject> renderableObjects = new CopyOnWriteArrayList<>();
        for (CopyOnWriteArrayList<RenderableObject> layer : renderableLayers) {
            renderableObjects.addAll(layer);
        }
        return renderableObjects;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);

    public abstract void loadObjects(ScreenManager screenManager);

    public abstract int getLoadData();


}
