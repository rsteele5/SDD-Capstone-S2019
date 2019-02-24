package gamescreens.screens.menus.dev;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gamescreens.screens.InventoryScreen;
import gamescreens.screens.Level;
import gamescreens.screens.TestGameplayScreen;
import gamescreens.screens.VendorScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import java.util.concurrent.CopyOnWriteArrayList;


public class DevScreen extends GameScreen {
    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    private float alphaTransition = 0.0f;
    //endregion

    //region <Construction and Initialization>
    public DevScreen(ScreenManager screenManager) {
        super(screenManager, "DevScreen", true);
    }

    @Override
    protected void initializeScreen() {
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-DevMenu.png", DrawLayer.Background));
        //Create buttons
        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/buttons/Button-Loading.png",
                DrawLayer.Entity,
                0,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Loading");
                    screenManager.addScreen(new TestGameplayScreen(screenManager));
                    //TODO: Add Loading Screen
                }));

        addObject(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Physics.png",
                DrawLayer.Entity,
                0,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Physics");
                    this.screenManager.addScreen(new Level(screenManager));
                }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Inventory.png",
                DrawLayer.Entity,
                0,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    //TODO: Add Inventory Screen
                    screenManager.addScreen(new InventoryScreen(screenManager));
                }));

        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-MainMenu.png",
                DrawLayer.Entity,
                0,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    this.setScreenState(ScreenState.TransitionOff);
                }));

        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON - 160,
                "/assets/buttons/Button-Vendor.png",
                DrawLayer.Entity,
                0,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Vendor");
                    screenManager.addScreen(new VendorScreen(screenManager));
                }));
    }

    //endregion

    @Override
    protected void transitionOn() {
        defaultTransitionOn();
    }

    @Override
    protected void transitionOff() {
        defaultTransitionOff();
    }

    @Override
    protected void hiddenUpdate() {
//        if(!screenManager.coveredByOverlay(this))
//            currentState = ScreenState.TransitionOff;
//        else
//            activeUpdate();
    }

    @Override
    protected void activeUpdate() {

    }
}
