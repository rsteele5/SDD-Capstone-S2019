package gamescreens.screens.gameplay.overworld;

import gameobjects.renderables.buttons.Button;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.Overlay;
import gamescreens.ScreenManager;
import gamescreens.screens.InventoryScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class OverworldUI extends Overlay {

    private Button actionButton;
    private Button inventoryButton;

    public OverworldUI(ScreenManager screenManager, GameScreen parentScreen) {
        super(screenManager, parentScreen,"OverworldUI", 0,0, 1f);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {

        inventoryButton = new Button(20,20, "", DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    screenManager.addScreen(new InventoryScreen(screenManager));
                });
        inventoryButton.addToScreen(this, true);

    }
}
