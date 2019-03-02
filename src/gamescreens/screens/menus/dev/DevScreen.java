package gamescreens.screens.menus.dev;

import _test.TestDialogBoxScreen;
import gameobjects.renderables.TempPlayerClass;
import gameobjects.renderables.Vendor;
import gameobjects.renderables.buttons.ButtonText;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gamescreens.ScreenManager;
import gamescreens.screens.Gameplay.Level.BedroomLevel;
import gamescreens.screens.Gameplay.Level.LevelManager;
import gamescreens.screens.InventoryScreen;
import gamescreens.screens.Level;
import gamescreens.screens.VendorScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;


public class DevScreen extends GameScreen {
    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    public static Vendor vendor;
    public static TempPlayerClass player;

    //endregion

    //region <Construction and Initialization>
    public DevScreen(ScreenManager screenManager) {
        super(screenManager, "DevScreen", true);
    }

    @Override
    protected void initializeScreen() {
        //Background image
        ImageContainer imageContainer;

        //Vendor and Player initialization
        vendor = new Vendor(570,335);
        player = new TempPlayerClass(330, 335);

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-DevMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create buttons
        Font noScary = new Font("NoScary", Font.PLAIN, 70);
        Color color = Color.WHITE;
        String imagePath = "/assets/buttons/Button-Empty.png";
        ButtonText button;

        button = new ButtonText(X_INIT_BUTTON,Y_INIT_BUTTON - 128,
                imagePath, DrawLayer.Entity, noScary, "TEST", color);
        button.setOnClick(() ->{
            Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Test");
            screenManager.addScreen(new TestDialogBoxScreen(screenManager)); });
        button.addToScreen(this, true);

        button = new ButtonText(X_INIT_BUTTON+(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON - 128,
                imagePath, DrawLayer.Entity, noScary, "New Game", color);
        button.setOnClick(() ->{
            Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Level");
            screenManager.addScreen(LevelManager.create(screenManager, new BedroomLevel())); });
        button.addToScreen(this, true);

        button = new ButtonText(X_INIT_BUTTON,Y_INIT_BUTTON,
                imagePath, DrawLayer.Entity, noScary, "Vendor", color);
        button.setOnClick(() ->{
            Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Vendor");
            screenManager.addScreen(new VendorScreen(screenManager)); });
        button.addToScreen(this, true);

        button = new ButtonText(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                imagePath, DrawLayer.Entity, noScary, "Physics", color);
        button.setOnClick(() ->{
            Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Physics");
            exiting = true;
            screenManager.addScreen(new Level(screenManager)); });
        button.addToScreen(this, true);

        button = new ButtonText(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON,
                imagePath, DrawLayer.Entity, noScary, "Inventory", color);
        button.setOnClick(() ->{
            Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
            screenManager.addScreen(new InventoryScreen(screenManager)); });
        button.addToScreen(this, true);

        button = new ButtonText(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                imagePath, DrawLayer.Entity, noScary, "MainMenu", color);
        button.setOnClick(() ->{
            Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
            setScreenState(ScreenState.TransitionOff); });
        button.addToScreen(this, true);

        // Initialize vendor

    }

    //endregion
}
