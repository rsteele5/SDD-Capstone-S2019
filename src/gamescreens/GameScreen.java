package gamescreens;

import gameobjects.renderables.RenderableObject;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameScreen {

    //region <Variables>
    public GameScreen parentScreen;

    public String name;

    protected ScreenData screenData;

    //TODO: Used for testing, remove after screen management is working. If it so tickles your pickles
    protected ScreenState previousState;

    protected ScreenManager screenManager;

    protected boolean loadingScreenRequired = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    protected boolean isExclusive = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    private boolean isLoading;

    /**
     *  The variable overlay describes if a screen is covering another screen in it's entirety, but
     *  does not prevent updates or rendering on screens below it in the list.
     */
    protected boolean isOverlay = false;

    protected boolean exiting = false;


    /**
     *  <p>Screen state describes all possible states that a screen can be in:</p>
     *  <p><b>TransitionOn</b> - The screen is currently undergoing transition on effects. ie fade in etc</p>
     *  <p><b>Active</b> - The screen is currently active and can accept input and update its objects</p>
     *  <p><b>TransitionOff</b> - The screen is currently undergoing transition off effects. ie fade in etc</p>
     *  <p><b>Hidden</b> - The screen is currently covered by another screen</p>
     */
    public enum ScreenState{
        TransitionOn,
        Active,
        TransitionOff,
        Hidden
    }

    /**
     *  Current state describes what state the screen is currently in.
     */
    protected ScreenState currentState;
    //endregion

    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager, String name) {
        this.screenManager = screenManager;
        previousState = null;
        screenData = new ScreenData();
        this.name = name;
        initializeScreen();
        currentState = ScreenState.TransitionOn;
        isLoading = true;
        loadContent();
    }

    /* Only for non root screen */
    public GameScreen(ScreenManager screenManager, String name, GameScreen parentScreen, boolean isExclusive) {
        this(screenManager, name);
        this.parentScreen = parentScreen;
        this.isExclusive = isExclusive;
        this.isOverlay = !isExclusive;
    }


    /**
     *  Initializes all of the stuff you want on your screen
     */
    protected abstract void initializeScreen();

    /**
     *  Loads the contents of this main.Game Screen.
     */
    private void loadContent() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            screenData.load();
            screenManager.addScreenData(screenData, isExclusive, isOverlay);
            isLoading = false;
        });
        executorService.shutdown();
    }
    //endregion

    //region <Getters and Setters>
    public boolean isLoadingScreenRequired(){
        return loadingScreenRequired;
    }

    public boolean isExclusive(){
        return isExclusive;
    }

    public boolean isOverlay(){
        return isOverlay;
    }

    public boolean isLoading(){
        return isLoading;
    }

    /**
     *  Returns true if a screen is active and can accept input or updates
     */
    public boolean isActive(){
        return currentState == ScreenState.Active;
    }

    public boolean isHidden(){return currentState == ScreenState.Hidden;}

    public boolean isExiting(){return exiting;}

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }

    public ScreenData getScreenData(){return screenData;}
    //endregion

    //region <Update>
    protected void transitionOn() {
        currentState = ScreenState.Active;
    }
    protected void transitionOff(){
        exiting = true;
    }

    //Override if you know what ur doing
    protected void hiddenUpdate() {}

    protected abstract void activeUpdate();

    /**
     *  Updates the state of the screen
     */
    public void update(){
        if(currentState != previousState){
            previousState = currentState;
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-CurrentState: " + currentState.name());
        }
        switch(currentState) {
            case TransitionOn: transitionOn(); break;
            case TransitionOff: transitionOff(); break;
            case Active: activeUpdate(); break;
            case Hidden: hiddenUpdate(); break;
            default: Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Unknown screen state");
        }
    }
    //endregion

    //region <Support Functions>
    public abstract void handleClickEvent(int x, int y);
    public void reset(){
        previousState = null;
        exiting = false;
    }
    //endregion
}
