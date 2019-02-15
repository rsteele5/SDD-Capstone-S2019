package _test.splashscreentest;

import control.ScreenManager;
import model.levels.Level;
import utilities.Debug;
import utilities.DebugEnabler;
import view.screens.GameScreen;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestGameplayScreen extends GameScreen {


    //region <Variable Declarations>
    private Level currentLevel;
    //endregion


    //region <Construction and Initialization>
    public TestGameplayScreen(ScreenManager screenManager) {
        name = "TestGameplayScreen";
        this.screenManager = screenManager;
        loadingScreenRequired = true;
        currentLevel = new TestLevel();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            loadContent();
            loading = false;
        });
        executorService.shutdown();
    }

    @Override
    protected void initializeScreen() { }

    @Override
    protected void loadContent() {
        Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");

        int dataLoaded = 0;

        int totalDataToLoad = currentLevel.getLoadData();
        screenManager.initializeLoadingScreen(totalDataToLoad);
        currentLevel.loadObjects(screenManager);

        Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Completed");
    }
    //endregion

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
        currentLevel.update();
    }

    //endregion

    //region<Draw>
    public void draw(Graphics2D graphics2D){
        Debug.success(DebugEnabler.RENDERABLE_LOG, name+" Draw");
        currentLevel.draw(graphics2D);
    }
    //endregion

    //region<Getters and Setters>
    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    //endregion

    //region<Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {

    }
    //endregion
}
