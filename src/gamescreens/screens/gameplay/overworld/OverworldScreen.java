package gamescreens.screens.gameplay.overworld;

import gameengine.GameEngine;
import gameobjects.renderables.house.HouseTile;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    //Maybe make this into room variables
    private GridContainer grassTileContainer;
    private GridContainer houseTileContainer;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 1f);
        //Overlay
        UI = new OverworldUI(screenManager, this);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {

        //House generation
        grassTileContainer = new GridContainer(this, 4, 3, HouseTile.SIZE, HouseTile.SIZE,0, 0,0);
        houseTileContainer = new GridContainer(this, 2, 2, HouseTile.SIZE, HouseTile.SIZE,0, 0,0);

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


        //Player

//        GameEngine.players.get(0).reset();
//        GameEngine.players.get(0).addToScreen(this,true);




    }

    @Override
    protected void transitionOn() {
        if(overlayScreens.isEmpty()){
            Debug.warning(DebugEnabler.GAME_SCREEN_LOG, name + " - Trying to add overlay");
            addOverlay(UI);
        }
    }
}