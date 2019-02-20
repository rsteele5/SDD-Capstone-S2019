package gamescreens;

import gameengine.physics.Kinematic;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenData {

    protected CopyOnWriteArrayList<GameObject> gameObjects;
    protected ArrayList<Kinematic> kinematics;
    protected ArrayList<RenderableObject> backgroundLayer;
    protected ArrayList<RenderableObject> sceneryLayer;
    protected ArrayList<RenderableObject> entityLayer;
    protected ArrayList<RenderableObject> effectsLayer;

    public enum DrawLayers{
        Background,
        Scenery,
        Entity,
        Effects
    }
}
