package gamescreens;

import gamescreens.screens.Level;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import gamescreens.screens.LoadingScreen;
import gamescreens.screens.TeamSplashScreen;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenManager {
    //region <Variables>
    private CopyOnWriteArrayList<GameScreen> gameScreens;

    private GameScreen rootScreen;

    private ScreenData screenData;

    private LoadingScreen loadingScreen;
    //endregion

    //region <Getters and Setters>
    public CopyOnWriteArrayList<GameScreen> getScreens() {
        return gameScreens;
    }
    //endregion

    public ScreenManager() {
        gameScreens = new CopyOnWriteArrayList<>();
        screenData = null;
        rootScreen = null;
        //
        loadingScreen = new LoadingScreen(this); //TODO: Change to LoadingScreen after Test complete.
        //add Splash screen to the
        addScreen(new TeamSplashScreen(this)); //TODO: Change to TeamSplashScreen after Test complete.
    }

    public void addScreenData(ScreenData data){
        screenData.addScreenData(data);
    }

    public void drawScreens(Graphics2D graphics){
        for (int i = gameScreens.size()-1; i >= 0; i--) {
            if (!gameScreens.get(i).isLoading()) {
                gameScreens.get(i).draw(graphics);
            }
        }
    }

    public void update() {
        boolean exclusiveAbove = false;

        for(GameScreen screen: gameScreens) {
            screen.update();
            if (!screen.isLoading()) {
                if (screen.isExiting()) {
                    removeScreen(screen);
                } else if (!exclusiveAbove) {
                    screen.update();
                    exclusiveAbove = screen.isExclusivePopup();
                }
            }else if (!exclusiveAbove){
                exclusiveAbove = screen.isExclusivePopup();
            }
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
        if(gameScreens.indexOf(screen) == 0){
            for(int i = 1; i < gameScreens.size(); i++) {
                gameScreens.get(i).setScreenState(GameScreen.ScreenState.Active);
                if(!gameScreens.get(i).isOverlay())
                    break;
            }
        }
        gameScreens.remove(screen);
        screen.reset();
    }

    public void initializeLoadingScreen(int amountOfData){
        loadingScreen.initializeLoadingScreen(amountOfData);
    }

    public void updateLoadingScreen(int dataLoaded){
        loadingScreen.dataLoaded(dataLoaded);
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
        for(GameScreen screen: gameScreens) {
            if(screen.isActive()) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Handle Click at x: " + x + ", y: " + y);
                screen.handleClickEvent(x,y);
            }
            else if(screen.isHidden()){
                return;
            }
        }
    }
    //endregion
}
