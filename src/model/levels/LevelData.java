package model.levels;

import control.ScreenManager;
import control.physics.Kinematic;
import model.gameobjects.*;
import model.gameobjects.renderable.dynamicObject.player.Player;
import model.gameobjects.renderable.dynamicObject.enemy.Enemy;
import model.gameobjects.renderable.RenderableObject;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class LevelData {

    protected final String name = "LevelData";
    protected Player player;
    protected CopyOnWriteArrayList<GameObject> gameObjects;
    protected CopyOnWriteArrayList<GameObject> onScreen;
    protected CopyOnWriteArrayList<Kinematic> kinematicObjects;
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<RenderableObject>> renderableLayers;
    protected CopyOnWriteArrayList<Enemy> enemies;
    //protected CopyOnWriteArrayList staticObjects;

    public abstract void initializeData();


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

    public CopyOnWriteArrayList<Kinematic> getKinematicObjects(){
        return kinematicObjects;
    }
}
