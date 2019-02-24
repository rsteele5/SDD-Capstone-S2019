package gamescreens.screens;

import _test.Square;
import gameengine.rendering.RenderEngine;
import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameengine.physics.Kinematic;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level extends GameScreen {
    private CopyOnWriteArrayList<GameObject> onScreen;
    private CopyOnWriteArrayList<Kinematic> kinematicObjects;

    public Level(ScreenManager screenManager) {
        super(screenManager, "Level", true);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {
        String bg = "/assets/backgrounds/BG-BlackCover.png";
        String path = "/assets/testAssets/square.png";

        addObject(new ImageContainer(0,0, bg, DrawLayer.Background));
        addObject(new Square(50,50,path, DrawLayer.Entity));
        addObject(new Square(50,200,path,DrawLayer.Entity));
        addObject(new Square(50,400,path,DrawLayer.Entity));
        addObject(new Square(50,600,path,DrawLayer.Entity));

        addObject(new Square(100,75,path,DrawLayer.Entity));
        addObject(new Square(100,300,path,DrawLayer.Entity));
        addObject(new Square(100,450,path,DrawLayer.Entity));
        addObject(new Square(100,527,path,DrawLayer.Entity));
        addObject(new Square(100,600,path,DrawLayer.Entity));

        addObject(new Square(200,50,path,DrawLayer.Entity));
        addObject(new Square(200,150,path,DrawLayer.Entity));
        addObject(new Square(200,250,path,DrawLayer.Entity));
        addObject(new Square(200,350,path,DrawLayer.Entity));
        addObject(new Square(200,450,path,DrawLayer.Entity));
        addObject(new Square(200,700,path,DrawLayer.Entity));

        addObject(new Square(400,50,path,DrawLayer.Entity));
        addObject(new Square(400,100,path,DrawLayer.Entity));
    }

    @Override
    protected void transitionOn() {
        setScreenState(ScreenState.Active);
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }

    public ArrayList<GameObject> levelObjects(){
        return gameObjects;
    }


}
