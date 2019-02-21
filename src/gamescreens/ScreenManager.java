package gamescreens;

import gamescreens.screens.TeamSplashScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class ScreenManager {
    //region <Variables>
    private GameScreen rootScreen;

    //private LoadingScreen loadingScreen;
    //endregion

    //region <Getters and Setters>

    //endregion

    public ScreenManager() {
        rootScreen = null;
        //loadingScreen = new LoadingScreen(this); //TODO: Change to LoadingScreen after Test complete.
        //add Splash screen to the
        addScreen(new TeamSplashScreen(this, "TeamSplashScreen"));
    }

    public void update() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "ScreenManager-update root: ");
        rootScreen.update();
    }

    //region <Support Functions>
    public void addScreen(GameScreen screen) {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,  "ScreenManager-add screen");
        if(rootScreen == null) rootScreen = screen;
        else rootScreen.cover(screen);
    }

    public void removeScreen(GameScreen screen) {
        rootScreen.uncover(screen);
    }
    //TODO: Figure it out later for Loading Screen
    public void initializeLoadingScreen(int amountOfData){
        //loadingScreen.initializeLoadingScreen(amountOfData);
    }

    //TODO: Figure it out later for Loading Screen
    public void updateLoadingScreen(int dataLoaded){
        //loadingScreen.dataLoaded(dataLoaded);
    }

    public void clickEventAtLocation(int x, int y) {
        rootScreen.handleClickEvent(x,y);
    }

    public void draw(Graphics2D graphics) {
        rootScreen.draw(graphics);
    }
    //endregion
}
