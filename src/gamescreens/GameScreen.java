package gamescreens;

import gameengine.physics.Kinematic;
import gameobjects.Clickable;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.renderables.RenderableObject;
import gamescreens.screens.LoadingScreen;
import gamescreens.screens.menus.playercount.PlayerCountScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import main.utilities.Loadable;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameScreen {

    //region <Variables>
    public String name;
    protected float screenAlpha;

    protected int x, y;

    private GameScreen childScreen;
    protected ArrayList<GameScreen> overlayScreens;
    public LoadingScreen loadingScreen;

    //TODO: Used for testing, remove after screen management is working. If it so tickles your pickles
    protected ScreenState previousState;

    protected ScreenManager screenManager;

    protected boolean loadingScreenRequired = false;

    /**
     * The variable exclusivePopup describes if a screen is covering a portion of another screen.
     * An exclusive popup screen prevents updates on screens below it in the list.
     */
    protected boolean isExclusive = false;
    protected boolean isOverlay = false;

    /**
     * The variable exclusivePopup describes if a screen is covering a portion of another screen.
     * An exclusive popup screen prevents updates on screens below it in the list.
     */
    private boolean isLoading;

    /**
     * The variable overlay describes if a screen is covering another screen in it's entirety, but
     * does not prevent updates or rendering on screens below it in the list.
     */
    protected boolean isRoot;

    protected boolean exiting = false;

    public ArrayList<GameObject> inactiveObjects;
    public ArrayList<GameObject> activeObjects;
    public ArrayList<Clickable> clickables;
    public ArrayList<Kinematic> kinematics;
    public ArrayList<Loadable> loadables;
    public ArrayList<RenderableObject> renderables;

    public void coverWith(GameScreen gameScreen) {
        if (gameScreen.isExclusive) {
            if (childScreen == null) {
                childScreen = gameScreen;
            } else {
                childScreen.coverWith(gameScreen);
            }
        } else {
            addOverlay(gameScreen);
        }
    }

    //Recursively removes all child screens and overlays
    public void removeMe(GameScreen gameScreen){
        if(gameScreen.childScreen != null)
            removeMe(gameScreen.childScreen);

        if(!gameScreen.overlayScreens.isEmpty()) {
            for (GameScreen overlay : gameScreen.overlayScreens)
                removeMe(overlay);
        }
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, gameScreen.name + " - I am being removed!!");
        gameScreen.currentState = ScreenState.TransitionOff;
    }

    //I don't know what this does
    public void uncoveredBy(GameScreen gameScreen) {
        if (childScreen == gameScreen) {
            if (childScreen.childScreen != null) {
                childScreen = childScreen.childScreen;
            } else {
                childScreen = null;
            }
        }
    }


    public ArrayList<Kinematic> getPhysicsObjects() {
        if (!isLoading) {
            if (childScreen != null) {
                return childScreen.getPhysicsObjects();
            } else {
                return kinematics;
            }
        } else {
            return null;
        }
    }

    public void setChildScreen(GameScreen screen) {
        childScreen = screen;
    }
    public GameScreen getChildScreen() {
        return childScreen;
    }


    /**
     * <p>Screen state describes all possible states that a screen can be in:</p>
     * <p><b>TransitionOn</b> - The screen is currently undergoing transition on effects. ie fade in etc</p>
     * <p><b>Active</b> - The screen is currently active and can accept input and update its objects</p>
     * <p><b>TransitionOff</b> - The screen is currently undergoing transition off effects. ie fade in etc</p>
     * <p><b>Hidden</b> - The screen is currently covered by another screen</p>
     */
    public enum ScreenState {
        TransitionOn,
        Active,
        TransitionOff,
        Hidden
    }

    /**
     * Current state describes what state the screen is currently in.
     */
    protected ScreenState currentState;
    //endregion

    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager, String name, float screenAlpha) {
        this.screenManager = screenManager;
        this.name = name;
        this.isRoot = true;
        previousState = null;
        this.screenAlpha = screenAlpha;
        x = 0;
        y = 0;
        overlayScreens = new ArrayList<>();
        //GameObjects
        activeObjects = new ArrayList<>();
        inactiveObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        renderables = new ArrayList<>();
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + " - is initializing");
        initializeScreen();
        Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + " - initialized");
        currentState = ScreenState.TransitionOn;
        isLoading = true;
        loadContent();
    }

    public GameScreen(ScreenManager screenManager, String name) {
        this(screenManager, name, 0f);
    }


    /* Only for non root screen */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive) {
        this(screenManager, name, isExclusive, 0, 0, 0f);
    }
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, float screenAlpha) {
        this(screenManager, name, isExclusive, 0, 0, screenAlpha);
    }
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos) {
        this(screenManager, name, isExclusive, xPos, yPos, 0f);
    }

    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos, float screenAlpha) {
        this.screenManager = screenManager;
        this.name = name;
        this.isRoot = false;
        this.isExclusive = isExclusive;
        this.isOverlay = !isExclusive;
        previousState = null;
        this.screenAlpha = screenAlpha;
        x = xPos;
        y = yPos;
        overlayScreens = new ArrayList<>();
        //Game Objects
        activeObjects = new ArrayList<>();
        inactiveObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        renderables = new ArrayList<>();
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + " - is initializing");
        initializeScreen();
        Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + " - initialized");
        currentState = ScreenState.TransitionOn;
        isLoading = true;
        loadContent();
    }


    /**
     * Initializes all of the stuff you want on your screen
     */
    protected abstract void initializeScreen();

    /**
     * Loads the contents of this main.Game Screen.
     */
    protected void loadContent() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + " - Load start");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            if (loadingScreenRequired) {
                loadingScreen = screenManager.getLoadingScreen();
                loadingScreen.initializeLoadingScreen(loadables.size());
                coverWith(loadingScreen);
                for (int i = 0; i < loadables.size(); i++) {
                    loadables.get(i).load();
                    loadingScreen.dataLoaded(i);
                }
                isLoading = false;
                childScreen = null;
                loadingScreen.reset();
            }
            for (Loadable loadable : loadables) loadable.load();
            loadables.clear();
            isLoading = false;
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + " - Loaded");
        });
        executorService.shutdown();
    }
    //endregion

    //region <Getters and Setters>
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public boolean isLoadingScreenRequired() {
        return loadingScreenRequired;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public boolean isLoading() {
        return isLoading;
    }

    /**
     * Returns true if a screen is active and can accept input or updates
     */
    public boolean isActive() {
        return currentState == ScreenState.Active;
    }

    public boolean isHidden() {
        return currentState == ScreenState.Hidden;
    }

    public boolean isExiting() {
        return exiting;
    }

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }


    //endregion

    //region <Update>
    protected void transitionOn() {
        if(screenAlpha < 0.9f){
            screenAlpha += 0.05f;
            setScreenAlpha(screenAlpha);
        } else {
            setScreenAlpha(1.0f);
            currentState = ScreenState.Active;
        }
    }

    protected void transitionOff() {
        if(screenAlpha > 0.075f){
            screenAlpha -= 0.07f;
            setScreenAlpha(screenAlpha);
        } else {
            exiting = true;
        }
    }

    //Override if you know what ur doing
    protected void hiddenUpdate() {}

    protected void activeUpdate() {
        for(GameObject activeObject: activeObjects){
            activeObject.update();
        }
    }

    /**
     *  Updates the state of the screen
     */
    public void update(){
        // If I have an Exclusive child screen on top on me
        if(childScreen != null) {
            if(childScreen.isExiting()){
                if(!isLoading){
                    childScreen = null;
                }
            } else {
                childScreen.update();
            }
        } else {
            switch(currentState) {
                case TransitionOn: transitionOn(); break;
                case TransitionOff: transitionOff(); break;
                case Active: activeUpdate(); break;
                case Hidden: hiddenUpdate(); break;
                default: Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Unknown screen state");
            }

            if(!overlayScreens.isEmpty()) {
                for (GameScreen overlay : overlayScreens)
                    overlay.update();
            }

            if(currentState != previousState){
                previousState = currentState;
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-CurrentState: " + currentState.name());
            }
        }
    }

    public final void drawScreen(Graphics2D graphics) {
        if(!isLoading) {
            drawLayers(graphics);
            if(childScreen != null) {
                childScreen.drawScreen(graphics);
            }
            for(GameScreen overlay : overlayScreens)
                overlay.drawScreen(graphics);
        } else {
            if(childScreen != null) {
                childScreen.drawScreen(graphics);
            }
        }
    }

    protected void drawLayers(Graphics2D graphics) {
        for(RenderableObject renderable : renderables)
            renderable.draw(graphics);
    }
    //endregion

    //region <Support Functions>
    public boolean handleClickEvent(int x, int y) {
        //Handle click on the Exlusive screen covering me
        if(childScreen != null) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle click on child");
            childScreen.handleClickEvent(x,y);
        } else {
            //Handle click on all overlays
            for (GameScreen overlay : overlayScreens) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle click on overlay");
                if (overlay.handleClickEvent(x, y))
                    return true;
            }
            // If no overlays handled clicks
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-screen handle click ");
            for(Clickable thing: clickables) {
                if(thing.contains(x,y)) {
                    thing.onClick();
                    return true;
                }
            }
        }
        return false;
    }

    public void reset(){
        previousState = null;
        exiting = false;
    }

    protected void addOverlay(GameScreen overlay){
        if(!overlay.isOverlay){
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,
                    overlay.name +"- is not an overlay. Will not add to overlays.");
        } else {
            overlayScreens.add(overlay);
        }
    }

    protected void setScreenAlpha(float alpha){
        screenAlpha = alpha;
        for(RenderableObject renderable : renderables)
            renderable.setAlpha(screenAlpha);
    }
    //endregion
}
