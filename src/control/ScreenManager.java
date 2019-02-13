package control;

import utilities.Debug;
import utilities.DebugEnabler;
import view.screens.GameScreen;
import view.screens.LoadingScreen;
import view.screens.TeamSplashScreen;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenManager {
    //region <Variables>
    private CopyOnWriteArrayList<GameScreen> gameScreens;

    private LoadingScreen loadingScreen;
    //endregion

    //region <Getters and Setters>
    public CopyOnWriteArrayList<GameScreen> getScreens() {
        return gameScreens;
    }
    //endregion

    public ScreenManager() {
        gameScreens = new CopyOnWriteArrayList<>();
        //
        loadingScreen = new LoadingScreen(this); //TODO: Change to LoadingScreen after Test complete.
        //add Splash screen to the
        addScreen(new TeamSplashScreen(this)); //TODO: Change to TeamSplashScreen after Test complete.
    }

    public void drawScreens(Graphics2D graphics){
        for (GameScreen screen : gameScreens) {
            if (!screen.isLoading()) {
                screen.draw(graphics);
            }
        }
    }

    public void update() {
        for(GameScreen screen: gameScreens) {
            if(screen.isLoading()) { }
            else if(screen.isExiting()) {
                removeScreen(screen);
                screen.reset();
            }
            else
                screen.update();
        }
    }

    //region <Support Functions>
    public void addScreen(GameScreen screen) {
        if(!screen.isOverlay()){
            for(GameScreen gameScreen: gameScreens){
                gameScreen.setScreenState(GameScreen.ScreenState.Hidden);
            }
        }
        gameScreens.add(0,screen);
        if(screen.isLoadingScreenRequired()){
            gameScreens.add(0,loadingScreen);
        }
    }

    public void removeScreen(GameScreen screen) {
        gameScreens.remove(screen);
    }

    public void initializeLoadingScreen(int amountOfData){
        loadingScreen.initializeLoadingScreen(amountOfData);
    }

    public void updateLoadingScreen(int dataloaded){
        loadingScreen.dataLoaded(dataloaded);
    }

    public boolean coveredByOverlay(GameScreen screen){
        for(GameScreen screenCheck: gameScreens){
            if(screen == screenCheck)
                return false;
            else if(screenCheck.isOverlay())
                return true;
        }
        return false;
    }

    public boolean coveredByLoading(GameScreen screen){
        for(GameScreen screenCheck: gameScreens){
            if(screen == screenCheck)
                return false;
            else if(screenCheck.isLoading())
                return true;
        }
        return false;
    }

    public void clickEventAtLocation(int x, int y) {
        //Check active screen
        for(GameScreen gameScreen: gameScreens) {
            if(gameScreen.isActive()) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Handle Click at x: " + x + ", y: " + y);
                gameScreen.handleClickEvent(x,y);
                return;
            }
        }
    }
    //endregion
}
