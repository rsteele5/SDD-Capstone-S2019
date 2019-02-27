package gamescreens.screens.Gameplay;

import gameobjects.renderables.house.HouseTile;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class OverworldScreen extends GameScreen {

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 1f);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {
        //TODO: Player

        HouseTile tileTest = new HouseTile(50,50);
        tileTest.addToScreen(this,true);

        Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + " - is initializing");
    }
}
