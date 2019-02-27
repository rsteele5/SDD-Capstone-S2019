package gamescreens;

import gameengine.GameSettings;
import gameengine.physics.Kinematic;
import gamescreens.screens.LoadingScreen;
import gamescreens.screens.TeamSplashScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.ArrayList;

public class ScreenManager {
    //region <Variables>
    private GameScreen rootScreen;
    private LoadingScreen loadingScreen;
    private GameSettings gameSettings;
    //endregion
    //region <Getters and Setters>
    public GameSettings getGameSettings() {
        return gameSettings;
    }
    //endregion
    public ScreenManager(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        rootScreen = null;
        loadingScreen = new LoadingScreen(this); //TODO: Change to LoadingScreen after Test complete.
        //add Splash screen to the
        rootScreen = new TeamSplashScreen(this, "TeamSplashScreen");
    }

    public void update() {
        rootScreen.update();
    }

    //region <Support Functions>
    public void addScreen(GameScreen screen) {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,  "ScreenManager-add screen " + screen.name);
        if(screen.isRoot){
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, screen.name + " - is a Root");
            rootScreen.coverWith(screen);
            rootScreen = screen;
        } else {
            rootScreen.coverWith(screen);
        }
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
        if(rootScreen.handleClickEvent(x,y))
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, "ScreenManager handled this click");
    }

    public void draw(Graphics2D graphics) {
        rootScreen.drawScreen(graphics);
    }

    public LoadingScreen getLoadingScreen() {
        return loadingScreen;
    }

    public ArrayList<Kinematic> getPhysicsObjects() {
        return rootScreen.getPhysicsObjects();
    }
    //endregion
}
