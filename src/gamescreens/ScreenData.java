package gamescreens;

import gameengine.physics.Kinematic;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import main.utilities.Loadable;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static gamescreens.DrawLayer.*;

public class ScreenData {

    protected CopyOnWriteArrayList<GameObject> gameObjects;
    protected ArrayList<Kinematic> kinematics;
    protected ArrayList<Loadable> loadables;
    private ArrayList<RenderableObject> hiddenScreens;
    protected ArrayList<RenderableObject> backgroundLayer;
    protected ArrayList<RenderableObject> sceneryLayer;
    protected ArrayList<RenderableObject> entityLayer;
    protected ArrayList<RenderableObject> effectsLayer;
    private ArrayList<RenderableObject> overlayScreens;

    public ScreenData(){
        gameObjects = new CopyOnWriteArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        hiddenScreens = new ArrayList<>();
        backgroundLayer = new ArrayList<>();
        sceneryLayer = new ArrayList<>();
        entityLayer = new ArrayList<>();
        effectsLayer = new ArrayList<>();
        overlayScreens = new ArrayList<>();
    }

    public void prune(ScreenData data){
        gameObjects.removeAll(data.gameObjects);
        kinematics.removeAll(data.kinematics);
        loadables.removeAll(data.loadables);
        hiddenScreens.removeAll(data.hiddenScreens);
        backgroundLayer.removeAll(data.backgroundLayer);
        sceneryLayer.removeAll(data.sceneryLayer);
        entityLayer.removeAll(data.entityLayer);
        effectsLayer.removeAll(data.effectsLayer);
        overlayScreens.removeAll(data.overlayScreens);
    }

    public ArrayList<RenderableObject> getRenderables() {
        ArrayList<RenderableObject> renderables = new ArrayList<>();
        renderables.addAll(overlayScreens);
        renderables.addAll(effectsLayer);
        renderables.addAll(entityLayer);
        renderables.addAll(sceneryLayer);
        renderables.addAll(backgroundLayer);
        renderables.addAll(hiddenScreens);

        return renderables;
    }
    public CopyOnWriteArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<RenderableObject> getHiddenScreens() {
        return hiddenScreens;
    }
    public ArrayList<RenderableObject> getBackgroudLayer() {
        return backgroundLayer;
    }
    public ArrayList<RenderableObject> getSceneryLayer() {
        return sceneryLayer;
    }
    public ArrayList<RenderableObject> getEntityLayer() {
        return entityLayer;
    }
    public ArrayList<RenderableObject> getEffectsLayer() {
        return effectsLayer;
    }
    public ArrayList<RenderableObject> getOverlayScreens() {
        return overlayScreens;
    }

    public void addHidden(ArrayList<RenderableObject> renderables) {
        hiddenScreens.addAll(renderables);
    }

    public void addOverlay(ScreenData data) {
        overlayScreens.addAll(data.getRenderables());
    }

    public void add(RenderableObject renderable) {
        switch (renderable.getDrawLayer()){
            case Background: backgroundLayer.add(renderable);break;
            case Scenery: sceneryLayer.add(renderable);break;
            case Entity: entityLayer.add(renderable);break;
            case Effects: effectsLayer.add(renderable);break;
        }
        loadables.add(renderable);
        gameObjects.add(renderable);
        if(renderable instanceof Kinematic)
            kinematics.add((Kinematic) renderable);
    }

    public void load() {
        for (Loadable loadable : loadables) loadable.load();
    }

    public void draw(Graphics2D graphics) {
        for(RenderableObject hidden : hiddenScreens)
            hidden.draw(graphics);
        for(RenderableObject bg : backgroundLayer)
            bg.draw(graphics);
        for(RenderableObject scenery : sceneryLayer)
            scenery.draw(graphics);
        for(RenderableObject entity : entityLayer)
            entity.draw(graphics);
        for(RenderableObject effect : effectsLayer)
            effect.draw(graphics);
        for(RenderableObject overlay : overlayScreens)
            overlay.draw(graphics);
    }
}
