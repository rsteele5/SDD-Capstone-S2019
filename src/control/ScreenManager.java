package control;

import _test.splashscreentest.TestTeamSplashScreen;
import _test.splashscreentest.TestTitleScreen;
import model.gameobjects.Renderable;
import utilities.Log;
import view.renderengine.GameScreen;

import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenManager {

    private CopyOnWriteArrayList<GameScreen> gameScreens;

    public ScreenManager() {
        gameScreens = new CopyOnWriteArrayList<>();

        //Testing splash screen
        addScreen(new TestTeamSplashScreen(this));
    }

    public void addScreen(GameScreen screen) {
        for(GameScreen gameScreen: gameScreens){
            gameScreen.setScreenState(GameScreen.ScreenState.Hidden);
        }
        gameScreens.add(0,screen);
        screen.loadContent();
    }

    public void update() {
        for(GameScreen screen: gameScreens) {
            if(screen.isLoading()){
                return;
            }
            if(screen.isActive()){

            }
//            if(screen.isHidden()){
//
//            }
        }
    }

    public void removeScreen(GameScreen screen) {
        gameScreens.remove(screen);
    }

    public CopyOnWriteArrayList<GameScreen> getScreens() {
        return gameScreens;
    }


    public CopyOnWriteArrayList<Renderable> getObjectsAtLocation(int x, int y) {
        //Check active screen
        //Find objects on screen at x and y location
        CopyOnWriteArrayList<Renderable> renderables = new CopyOnWriteArrayList<>();

        for(GameScreen gameScreen: gameScreens) {
            Log.logError(gameScreen.getScreenState().name());
            if(gameScreen.isActive()) {
                for(Renderable renderable: gameScreen.getRenderables()){
                    if(renderable.getBoundingBox().contains(x,y)){
                        renderables.add(renderable);
                    }
                }
            }
        }

        return renderables;
    }
}
