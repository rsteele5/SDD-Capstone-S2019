package gamescreens.screens.gameplay.level;

import _test.Square;
import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobjects.Player;
import gameobjects.renderables.Floor;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameengine.physics.Kinematic;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.concurrent.CopyOnWriteArrayList;

public class Level extends GameScreen {
    private CopyOnWriteArrayList<GameObject> onScreen;
    private CopyOnWriteArrayList<Kinematic> kinematicObjects;

    private final int X_INIT_BUTTON = 64;
    protected final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;

    public Level(ScreenManager screenManager) {
        super(screenManager, "level", true);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {
        String bg = "/assets/backgrounds/BG-BlackCover.png";
        String path = "/assets/testAssets/square.png";
        RenderableObject player = GameEngine.players.get(0);

//        ((Player)player).reset();
//        Animator playerAnimator = new Animator(player);
//        playerAnimator.addAnimation("Idle", new PlayerIdleAnimation());
//        playerAnimator.setAnimation("Idle");
//        player.addAnimator(playerAnimator);


        ImageContainer background;
        background = (new ImageContainer(0,0, bg, DrawLayer.Background));
        background.addToScreen(this,true);
        GameEngine.players.get(0).setState(Player.PlayerState.sideScroll);
        GameEngine.players.get(0).addToScreen(this,true);
        Square square;
        Button b = (new Button(1000,100,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    setScreenState(ScreenState.TransitionOff);
                }));
        b.addToScreen(this,true);
        for(int x1 = 0; x1 < 5; x1++){
            for(int y1 = 0; y1 < x1; y1++){
                square = new Square(x1 * 75 + 100,y1*75,path,DrawLayer.Entity);
                square.addToScreen(this, true);
            }
        }

        Floor floor = new Floor(0,720, "/assets/testAssets/WoodTile2.png", DrawLayer.Entity);
        floor.setWidth(1280);
        floor.setHeight(30);
        floor.addToScreen(this, true);

        setCamera(new Camera(this, GameEngine.players.get(0)));
    }


    @Override
    protected void transitionOff(){
        GameEngine.players.get(0).setState(Player.PlayerState.asleep);
        exiting = true;
    }
}
