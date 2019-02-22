package gamescreens.screens;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gameobjects.renderables.labels.Label;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class MainMenuScreen extends GameScreen {

    //region <Variables>

    private HashMap<String, CopyOnWriteArrayList<Label>> savedLabels;

    private final int X_INIT_BUTTON = 64;
    private final int X_INIT_LABEL = 96;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public MainMenuScreen(ScreenManager screenManager) {
        super(screenManager, "MainMenuScreen");
        isRoot = true;
    }

    @Override
    protected void initializeScreen() {
        Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");
        savedLabels = new HashMap<>();
        //Create buttons
        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-NewGame.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - New Game");
                    //screenManager.addScreen(new PlayerCountScreen(screenManager));
        }));

        addObject(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Options.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager, savedLabels));
        }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Dev.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - DevMode");
                    screenManager.addScreen(new DevScreen(screenManager));
        }));

        //Create Background on layer 0
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-MainMenu.png", DrawLayer.Background));

        //Create labels
        CopyOnWriteArrayList<Label> controlLabels = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Label> graphicsLabels = new CopyOnWriteArrayList<>();

            //Add Control Menu labels
            controlLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, "/assets/labels/Label-Keyboard.png", DrawLayer.Entity, true));
            controlLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, "/assets/labels/Label-Gamepad.png", DrawLayer.Entity, false));
            //Add Graphics Menu labels
            graphicsLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, "/assets/labels/Label-Low.png", DrawLayer.Entity, false));
            graphicsLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, "/assets/labels/Label-Medium.png", DrawLayer.Entity, true));
            graphicsLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, "/assets/labels/Label-High.png", DrawLayer.Entity, false));
            //Add dynamic arrays to map
            savedLabels.put("Controls", controlLabels);
            savedLabels.put("Graphics", graphicsLabels);

        Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Success");
    }
    //endregion

    //region <Update>
    @Override
    protected void transitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {
//        if(!screenManager.coveredByOverlay(this))
//            currentState = ScreenState.TransitionOff;
//        else
//            activeUpdate();
    }

    @Override
    protected void activeUpdate(){

    }

    //endregion

    //region <Support Functions>

    //endregion

}
