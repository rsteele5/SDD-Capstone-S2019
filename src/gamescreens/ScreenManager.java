package gamescreens;

import main.utilities.Debug;
import main.utilities.DebugEnabler;
import gamescreens.screens.TeamSplashScreen;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenManager {
    //region <Variables>
    private CopyOnWriteArrayList<GameScreen> gameScreens;

    private GameScreen rootScreen;

    private ScreenData screenData;

    //private LoadingScreen loadingScreen;
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
        //loadingScreen = new LoadingScreen(this); //TODO: Change to LoadingScreen after Test complete.
        //add Splash screen to the
        addScreen(new TeamSplashScreen(this, "TeamSplashScreen"));
    }

    public void update() {
        boolean exclusiveAbove = false;

        for(GameScreen screen: gameScreens) {
            if (!screen.isLoading()) {
                if (screen.isExiting()) {
                    removeScreen(screen);
                } else if (!exclusiveAbove) {
                    screen.update();
                    exclusiveAbove = screen.isExclusive();
                }
            }else if (!exclusiveAbove){
                exclusiveAbove = screen.isExclusive();
            }
        }
    }

    //region <Support Functions>
    public void addScreen(GameScreen screen) {
        if(gameScreens.isEmpty() || (!screen.isExclusive() && !screen.isOverlay()))
            rootScreen = screen;

        if(!screen.isOverlay()){
            for(GameScreen gameScreen: gameScreens){
                gameScreen.setScreenState(GameScreen.ScreenState.Hidden);
            }
        }

        gameScreens.add(0,screen);

        if(screen.isLoadingScreenRequired()){
            //gameScreens.add(0,loadingScreen);
        }
    }

    public ScreenData getScreenData() {
        return screenData;
    }

    public void addScreenData(ScreenData data, boolean isExclusive, boolean isOverlay){
        if(isExclusive){
            data.addHidden(screenData.getRenderables());
            screenData = data;
        }
        else if(isOverlay){
            screenData.addOverlay(data);
        }
        else
            screenData = data;
    }

    public void removeScreen(GameScreen screen) {
        Debug.warning(DebugEnabler.GAME_SCREEN_LOG,screen.name+"-does not contian?");
        if(gameScreens.contains(screen)) {
            Debug.warning(DebugEnabler.GAME_SCREEN_LOG,screen.name+"-Trying to remove");
            if (gameScreens.indexOf(screen) == 0) {
                for (int i = 1; i < gameScreens.size(); i++) {
                    gameScreens.get(i).setScreenState(GameScreen.ScreenState.Active);
                    if (!gameScreens.get(i).isOverlay())
                        break;
                }
            }

            if (screen.isOverlay())
                screenData.prune(screen.getScreenData());
            else if (screen.isExclusive()) {
                for (int i = gameScreens.indexOf(screen)+1; i < gameScreens.size(); i++){
                    if(!gameScreens.get(i).isOverlay()) {
                        screenData = gameScreens.get(i).getScreenData();
                        break;
                    }
                }
            }
            gameScreens.remove(screen);
            screen.reset();
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

    public void draw(Graphics2D graphics) {
        screenData.draw(graphics);
    }
    //endregion
}
