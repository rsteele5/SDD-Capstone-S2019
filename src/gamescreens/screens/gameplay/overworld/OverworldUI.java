package gamescreens.screens.gameplay.overworld;

import gameobjects.renderables.buttons.Button;
import gameobjects.renderables.buttons.ButtonText;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.Overlay;
import gamescreens.ScreenManager;
import gamescreens.screens.InventoryScreen;
import gamescreens.screens.gameplay.level.BedroomLevel;
import gamescreens.screens.gameplay.level.LevelDecorator;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class OverworldUI extends Overlay {

    private Button actionButton;
    private Button inventoryButton;
    private static String inventoryBtnPath = "/assets/buttons/Button-Inventory.png";
    private static String fightBtnPath = "/assets/buttons/Button-Fight.png";
    private static String vendorBtnPath = "/assets/buttons/Button-Vendor.png";


    public OverworldUI(ScreenManager screenManager, GameScreen parentScreen) {
        super(screenManager, parentScreen,"OverworldUI", 0,0, 1f);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {

        inventoryButton = new Button(20,20, inventoryBtnPath, DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    screenManager.addScreen(new InventoryScreen(screenManager));
                });
        inventoryButton.addToScreen(this, true);

        actionButton = new ButtonText(350, 20,
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity, new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Fight!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - level");
                    //TODO save players location
                    parentScreen.setCamera(null);
                    screenManager.addScreen(LevelDecorator.create(screenManager, new BedroomLevel()));

        });
        actionButton.addToScreen(this, true);
    }
}
