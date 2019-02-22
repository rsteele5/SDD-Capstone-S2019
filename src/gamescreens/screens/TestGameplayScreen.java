package gamescreens.screens;

import gameengine.rendering.RenderEngine;
import gamescreens.DrawLayer;
import gamescreens.ScreenManager;
import gamescreens.GameScreen;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestGameplayScreen extends GameScreen {

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    //region <Construction and Initialization>
    public TestGameplayScreen(ScreenManager screenManager) {
        super(screenManager, "TestGamePlayScreen");
        name = "TestGameplayScreen";
        loadingScreenRequired = true;
        isExclusive = true;
    }

    @Override
    protected void initializeScreen() {
        int dataLoaded = 0;
        int totalDataToLoad = 351;
        screenManager.initializeLoadingScreen(totalDataToLoad);

        for(int i = 0; i < 50; i++) {
            addObject(new ImageContainer(0,0, "/assets/testAssets/largeImage.png", DrawLayer.Entity));
            ++dataLoaded;
            screenManager.updateLoadingScreen(dataLoaded);
        }

        for(int i = 0; i < 100; i++) {
            addObject(new ImageContainer(0,0, "/assets/testAssets/TestGameplayScreen.png", DrawLayer.Entity));
            ++dataLoaded;
            screenManager.updateLoadingScreen(dataLoaded);
        }


        for(int i = 0; i < 100; i++) {
            addObject(new ImageContainer(0,0, "/assets/testAssets/TestGameplayScreen1.png", DrawLayer.Effects));
            ++dataLoaded;
            screenManager.updateLoadingScreen(dataLoaded);
        }


        for(int i = 0; i < 100; i++) {
            addObject(new ImageContainer(0,0, "/assets/testAssets/TestGameplayScreen2.png", DrawLayer.Effects));
            ++dataLoaded;
            screenManager.updateLoadingScreen(dataLoaded);
        }

        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    this.setScreenState(ScreenState.TransitionOff);
                }));
        ++dataLoaded;
        screenManager.updateLoadingScreen(dataLoaded);
    }


    @Override
    public void transitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    public void transitionOff() {
        exiting = true;
    }

    @Override
    public void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }
}
