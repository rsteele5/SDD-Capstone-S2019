package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.ImageContainer;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TestLoadingScreen extends view.screens.GameScreen {

    private int totalDataToLoad = 0;
    ImageContainer loadingBar;
    private final int START_POINT_X = 63;
    private final int START_POINT_Y = 612;
    private final int MAX_WIDTH = 900;
    private final int MAX_HEIGHT = 75;
    private int progressRate = 0;
    private volatile double loadedData = 0.0;

    public TestLoadingScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "TestLoadingScreen";
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.TEST_LOG && DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Content");
            //Background
            BufferedImage background = ImageIO.read(getClass().getResource("/assets/LoadingScreen.png"));
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            //Foreground
            BufferedImage loadingBarBackgroundImage = ImageIO.read(getClass().getResource("/assets/LoadingBarBackground.png"));
            ImageContainer loadingBarBackground = new ImageContainer(START_POINT_X, START_POINT_Y, loadingBarBackgroundImage, 1);
            loadingBarBackground.setSize(MAX_WIDTH-2, MAX_HEIGHT);
            renderableLayers.get(1).add(loadingBarBackground);

            BufferedImage loadingBarImage = ImageIO.read(getClass().getResource("/assets/LoadingBar.png"));
            loadingBar = new ImageContainer(START_POINT_X, START_POINT_Y, loadingBarImage, 1);
            renderableLayers.get(1).add(loadingBar);

            Debug.success(DebugEnabler.TEST_LOG && DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }

    //region <Update>
    @Override
    public void updateTransitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    public void updateTransitionOff() {
        exiting = true;
    }

    @Override
    public void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {
        if(loadedData >= totalDataToLoad){
            currentState = ScreenState.TransitionOff;
        }else{
            loadingBar.setSize((int)(loadedData/totalDataToLoad * MAX_WIDTH), MAX_HEIGHT);
        }
    }

    @Override
    public void draw(Graphics2D graphics){
        super.draw(graphics);
        graphics.drawString("Loading: " + loadedData + "/" + totalDataToLoad, 500, 500);

    }

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {}

    @Override
    public void reset(){
        super.reset();
        totalDataToLoad = 0;
        progressRate = 0;
        loadedData = 0.0;
        loadingBar.setSize(loadingBar.getCurrentImage().getWidth(), loadingBar.getCurrentImage().getHeight());
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
