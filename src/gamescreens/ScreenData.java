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
    protected ArrayList<RenderableObject> midgroundLayer;
    protected ArrayList<RenderableObject> foregroundLayer;

    public enum DrawLayers{
        Background,
        Midground,
        Foreground
    }


}
