package model.levels;

import _test.splashscreentest.Square;
import control.RenderEngine;
import control.ScreenManager;
import control.physics.Kinematic;
import model.gameobjects.*;
import utilities.Debug;
import utilities.DebugEnabler;
import view.screens.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level extends GameScreen {
   // private CopyOnWriteArrayList<GameObject> gameObjects;
    private CopyOnWriteArrayList<GameObject> onScreen;
    private CopyOnWriteArrayList<Kinematic> kinematicObjects;
   // private CopyOnWriteArrayList<CopyOnWriteArrayList<RenderableObject>> renderableLayers;
    private CopyOnWriteArrayList<StaticObject> staticObjects;

    public Level(ScreenManager screenManager) {
        super(screenManager);
        name = "Level";
        exclusivePopup = true;
       // gameObjects = new CopyOnWriteArrayList<>();
    }

    @Override
    protected void initializeLayers() {
       // renderableLayers = new CopyOnWriteArrayList<>();
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    protected void loadContent() {
        try {
            BufferedImage square = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/testAssets/square.png")));
            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-BlackCover.png")));
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));
            renderableLayers.get(1).add(new Square(50,50,square,1));
            renderableLayers.get(1).add(new Square(50,200,square,1));
            renderableLayers.get(1).add(new Square(50,400,square,1));
            renderableLayers.get(1).add(new Square(50,600,square,1));

            renderableLayers.get(1).add(new Square(100,75,square,1));
            renderableLayers.get(1).add(new Square(100,300,square,1));
            renderableLayers.get(1).add(new Square(100,450,square,1));
            renderableLayers.get(1).add(new Square(100,527,square,1));
            renderableLayers.get(1).add(new Square(100,600,square,1));

            renderableLayers.get(1).add(new Square(200,50,square,1));
            renderableLayers.get(1).add(new Square(200,150,square,1));
            renderableLayers.get(1).add(new Square(200,250,square,1));
            renderableLayers.get(1).add(new Square(200,350,square,1));
            renderableLayers.get(1).add(new Square(200,450,square,1));
            renderableLayers.get(1).add(new Square(200,700,square,1));

            renderableLayers.get(1).add(new Square(400,50,square,1));
            renderableLayers.get(1).add(new Square(400,100,square,1));
            //Consolidate GameObjects
            for(CopyOnWriteArrayList<RenderableObject> layer: renderableLayers)
                gameObjects.addAll(layer);
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }

    @Override
    protected void updateTransitionOn() {
        setScreenState(ScreenState.Active);
    }

    @Override
    protected void updateTransitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }

    @Override
    public void handleClickEvent(int x, int y) {

    }

    public CopyOnWriteArrayList<GameObject> levelObjects(){
        return gameObjects;
    }


}
