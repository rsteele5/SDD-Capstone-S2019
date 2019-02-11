package control;

import _test.splashscreentest.TestLoadingScreen;
import _test.splashscreentest.TestTeamSplashScreen;
import utilities.Debug;
import utilities.DebugEnabler;
import view.renderengine.GameScreen;

import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenManager {

    private CopyOnWriteArrayList<GameScreen> gameScreens;

    //TODO: Change to LoadingScreen after Test complete
    private TestLoadingScreen loadingScreen;

    public ScreenManager() {
        gameScreens = new CopyOnWriteArrayList<>();
        //
        loadingScreen = new TestLoadingScreen(this); //TODO: Change to LoadingScreen after Test complete.
        //add Splash screen to the
        addScreen(new TestTeamSplashScreen(this)); //TODO: Change to SplashScreen after Test complete.
    }

    public void addScreen(GameScreen screen) {
        for(GameScreen gameScreen: gameScreens){
            gameScreen.setScreenState(GameScreen.ScreenState.Hidden);
        }
        gameScreens.add(0,screen);
    }

    public void update() {
        for(GameScreen screen: gameScreens) {
            if(screen.isLoading()) {
                break;
            }
            else if(screen.isExiting())
                removeScreen(screen);
            else if(screen.isHidden())
                screen.hiddenUpdate();
            else
                screen.update();
        }
    }

    public void removeScreen(GameScreen screen) {
        gameScreens.remove(screen);
    }

    public CopyOnWriteArrayList<GameScreen> getScreens() {
        return gameScreens;
    }


    public void clickEventAtLocation(int x, int y) {
        //Check active screen
        for(GameScreen gameScreen: gameScreens) {
            if(gameScreen.isActive()) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Handle Click at x: " + x + ", y: " + y);
                gameScreen.handleClickEvent(x,y);
            }
        }
    }
}
