package model.levels;

import model.gameobjects.*;

import java.util.concurrent.CopyOnWriteArrayList;

public class LevelData {

    private Player player;
    private CopyOnWriteArrayList<GameObject> gameObjects;
    private CopyOnWriteArrayList<GameObject> onScreen;
    private CopyOnWriteArrayList<Kinematic> kinematicObjects;
    private CopyOnWriteArrayList<CopyOnWriteArrayList<RenderableObject>> renderableLayers;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<StaticObject> staticObjects;

}
