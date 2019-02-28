package gamescreens.screens.gameplay.level;

import _test.Square;
import gameengine.GameEngine;
import gameengine.rendering.RenderEngine;
import gameobjects.Player;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameengine.physics.Kinematic;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import gamescreens.screens.menus.MainMenuScreen;
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

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;

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
        GameEngine.players.get(0).reset();
        ImageContainer background;
        background = (new ImageContainer(0,0, bg, DrawLayer.Background));
        background.addToScreen(this,true);
        GameEngine.players.get(0).addToScreen(this,true);
        Square square;
        Button b = (new Button(1000,100,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");

                    screenManager.addScreen(new MainMenuScreen(screenManager));
                    setScreenState(ScreenState.TransitionOff);

        }));
        b.addToScreen(this,true);
        square = new Square(100,200,path,DrawLayer.Entity);
        square.addToScreen(this, true);
        square = new Square(200,200,path,DrawLayer.Entity);
        square.addToScreen(this, true);
        square = new Square(200,300,path,DrawLayer.Entity);
        square.addToScreen(this, true);
        square = new Square(300,200,path,DrawLayer.Entity);
        square.addToScreen(this, true);
        square = new Square(300,300,path,DrawLayer.Entity);
        square.addToScreen(this, true);
        square = new Square(300,400,path,DrawLayer.Entity);;
        square.addToScreen(this, true);
        square = new Square(400,100,path,DrawLayer.Entity);
        square.addToScreen(this, true);
        //Debug.log(true,String.valueOf(GameEngine.players.size()));
       // Debug.log(true,String.valueOf(GameEngine.players.size()));

    }

    @Override
    protected void transitionOn() {
        setScreenState(ScreenState.Active);
    }
    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void transitionOff(){
        exiting = true;
    }

    @Override
    protected void activeUpdate() {
        super.activeUpdate();
        //Debug.log(true,"this " + String.valueOf(GameEngine.players.size()));
        //Debug.log(true,"global " + String.valueOf(GameEngine.players.size()));
    }
}
