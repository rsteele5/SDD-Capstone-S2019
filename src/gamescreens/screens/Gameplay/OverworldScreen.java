package gamescreens.screens.Gameplay;

import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameengine.rendering.animation.Animator;
import gameengine.rendering.animation.PlayerIdleAnimation;
import gameengine.rendering.animation.PlayerWalkingAnimation;
import gameobjects.Player;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.house.HouseTile;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.ArrayList;

public class OverworldScreen extends GameScreen {

    //Maybe make this into room variables
    private GridContainer grassTileContainer;
    private GridContainer houseTileContainer;

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 1f);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {
        grassTileContainer = new GridContainer(this, 4, 3, HouseTile.SIZE, HouseTile.SIZE,0, 0,0);
        houseTileContainer = new GridContainer(this, 2, 2, HouseTile.SIZE, HouseTile.SIZE,0, 0,0);

        setCamera(new Camera(this, GameEngine.players.get(0)));

        RenderableObject player = GameEngine.players.get(0);
        ((Player)player).reset();

        Animator playerAnimator = new Animator(player);
        playerAnimator.addAnimation("Walking", new PlayerWalkingAnimation());
        playerAnimator.setAnimation("Walking");
        player.addAnimator(playerAnimator);


        HouseTile grass;
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 3; col++){
                grass = new HouseTile(0,0, "/assets/overworld/grass/Overworld-Grass.png");
                grassTileContainer.addAt(grass,row,col);
            }
        }

        HouseTile bedroom;
        bedroom = new HouseTile(0,0, "/assets/overworld/bedroom/Overworld-Bedroom1.png");
        houseTileContainer.addAt(bedroom,0,0);
        bedroom = new HouseTile(0,0, "/assets/overworld/bedroom/Overworld-Bedroom2.png");
        houseTileContainer.addAt(bedroom,0,1);
        bedroom = new HouseTile(0,0, "/assets/overworld/bedroom/Overworld-Bedroom3.png");
        houseTileContainer.addAt(bedroom,1,0);
        bedroom = new HouseTile(0,0, "/assets/overworld/bedroom/Overworld-Bedroom4.png");
        houseTileContainer.addAt(bedroom,1,1);
        GameEngine.players.get(0).setState(Player.PlayerState.overWorld);
        Debug.log(true, String.valueOf(GameEngine.players.get(0).getState()));
        GameEngine.players.get(0).reset();
        GameEngine.players.get(0).addToScreen(this,true);




        Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + " - is initializing");
    }
}
