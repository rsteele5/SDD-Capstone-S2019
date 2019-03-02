package gamescreens.screens.gameplay.overworld;

import _test.VendorDialogBoxOverlayTest;
import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobjects.Player;
import gameobjects.renderables.Vendor;
import gameobjects.renderables.house.HouseTile;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    private VendorDialogBoxOverlayTest vendorDialogBox;
    //Maybe make this into room variables
    private GridContainer grassTileContainer;
    private GridContainer houseTileContainer;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 1f);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {

        //House generation
        grassTileContainer = new GridContainer(this, 4, 4, HouseTile.SIZE, HouseTile.SIZE,
                -HouseTile.SIZE, -HouseTile.SIZE,0);
        houseTileContainer = new GridContainer(this, 2, 2, HouseTile.SIZE, HouseTile.SIZE,0, 0,0);

        HouseTile grass;
        for(int row = 0; row < grassTileContainer.getRows(); row++){
            for(int col = 0; col < grassTileContainer.getCols(); col++){
                if (row <= 0 || row >= 3 || col <= 0 || col >= 3) {
                    grass = new HouseTile(0, 0, "/assets/overworld/grass/Overworld-Grass.png");
                    grassTileContainer.addAt(grass, row, col);
                }
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
        GameEngine.players.get(0).setState(Player.PlayerState.overWorld);
        Debug.log(true, String.valueOf(GameEngine.players.get(0).getState()));
        GameEngine.players.get(0).reset();
        GameEngine.players.get(0).addToScreen(this,true);
        setCamera(new Camera(this, GameEngine.players.get(0)));

        //Vendor
        Vendor vendor = GameEngine.vendor;
        vendor.setPosition(600,600);
        vendor.setImage("/assets/overworld/vendor/VendorOverworldForward.png");
        //TODO: make vendor trigger box
        vendor.addToScreen(this, true);

        //Overlay TODO: Fix layering
        UI = new OverworldUI(screenManager, this);
        vendorDialogBox = new VendorDialogBoxOverlayTest(screenManager,this, 460,100);
        addOverlay(UI);
        addOverlay(vendorDialogBox);



    }

//    @Override
//    protected void transitionOn() {
//        if(overlayScreens.isEmpty()){
//            Debug.warning(DebugEnabler.GAME_SCREEN_LOG, name + " - Trying to add overlay");
//            addOverlay(UI);
//        }
//    }
}
