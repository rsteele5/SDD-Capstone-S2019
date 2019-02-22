package gamescreens.screens;

import gameengine.rendering.RenderEngine;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoadingScreen extends GameScreen {

    //region <Variables>
    private int totalDataToLoad = 0;
    private ImageContainer loadingBar;
    private final int START_POINT_X = 63;
    private final int START_POINT_Y = 612;
    private final int MAX_WIDTH = 900;
    private final int MAX_HEIGHT = 75;
    private int progressRate = 0;
    private volatile double loadedData = 0.0;
    //endregion

    //region <Construction and Initialization>
    public LoadingScreen(ScreenManager screenManager) {
        super(screenManager, "LoadingScreen");
        isExclusive = true;
    }

    @Override
    protected void initializeScreen() {
        //Background
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-Loading.png", DrawLayer.Background));

        //Foreground
        ImageContainer loadingBarBackground = new ImageContainer(START_POINT_X -5, START_POINT_Y - 5, "/assets/LoadingBarBackground.png", DrawLayer.Entity);
        loadingBarBackground.setSize(MAX_WIDTH+10, MAX_HEIGHT + 10);
        addObject(loadingBarBackground);

        loadingBar = new ImageContainer(START_POINT_X, START_POINT_Y, "/assets/LoadingBar.png", DrawLayer.Entity);
        addObject(loadingBar);
    }

    //endregion

    //region <Update>
    @Override
    public void transitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    public void transitionOff() {
        exiting = true;
    }

    @Override
    public void hiddenUpdate() { }

    @Override
    protected void activeUpdate() {
        if(loadedData >= totalDataToLoad){
            currentState = ScreenState.TransitionOff;
        }else{
            loadingBar.setSize((int)(loadedData/totalDataToLoad * MAX_WIDTH), MAX_HEIGHT);
        }
    }
    //endregion

    //region <Render>
    @Override
    public void draw(Graphics2D graphics){
        graphics.setColor(Color.WHITE);
        graphics.drawString("Loading: " + (int)(loadedData) + "/" + totalDataToLoad, 500, 500);
    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {}

    @Override
    public void reset(){
        super.reset();
        totalDataToLoad = -1;
        progressRate = 0;
        loadedData = 0.0;
        loadingBar.setSize(loadingBar.getCurrentImage().getWidth(), loadingBar.getCurrentImage().getHeight());
        currentState = ScreenState.TransitionOn;
    }

    public void initializeLoadingScreen(int amount){
        totalDataToLoad = amount;
        progressRate = MAX_WIDTH/amount;
    }

    public void dataLoaded(int currentDataLoaded){
        loadedData = currentDataLoaded;
    }
    //endregion
}