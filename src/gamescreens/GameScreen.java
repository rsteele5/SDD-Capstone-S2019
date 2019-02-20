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

    private ScreenData screenData;

    //TODO: Used for testing, remove after screen management is working. If it so tickles your pickles
    protected ScreenState previousState;

    protected ScreenManager screenManager;

    protected boolean loadingScreenRequired = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    private boolean isExclusive = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    public boolean isLoading = false;

    /**
     *  The variable overlay describes if a screen is covering another screen in it's entirety, but
     *  does not prevent updates or rendering on screens below it in the list.
     */
    public boolean isOverlay = false;

    private boolean exiting = false;


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

    //region <Getters and Setters>
    public boolean isLoadingScreenRequired(){
        return loadingScreenRequired;
    }

    /**
     *  Returns true if a screen is active and can accept input or updates
     */
    public boolean isActive(){
        return currentState == ScreenState.Active;
    }

    public boolean isHidden(){return currentState == ScreenState.Hidden;}

    public boolean isExiting(){return currentState == ScreenState.Hidden;}

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }
    //endregion


    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        initializeScreen();
        currentState = ScreenState.TransitionOn;
        isLoading = true;
    }

    /* Only for non root screen */
    public GameScreen(ScreenManager screenManager, GameScreen parentScreen, boolean isExclusive) {
        this(screenManager);
        this.parentScreen = parentScreen;
        this.isExclusive = isExclusive;
        this.isOverlay = !isExclusive;
    }


    /**
     *  Initializes the renderableLayers array with an arbitrary amount of layers.
     */
    private void initializeScreen() {
        previousState = null;
        screenData = new ScreenData();
    }

    /**
     *  Loads the contents of this main.Game Screen.
     */
    protected void loadContent() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            screenData.load();
            screenManager.addScreenData(screenData);
            isLoading = false;
        });
        executorService.shutdown();
    }
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

    //region <Render>
    /**
     *  Draws the screen to the monitor
     */
    public void draw(Graphics2D graphics) {
        for (CopyOnWriteArrayList<RenderableObject> layer : renderableLayers) {
            for (RenderableObject gameObject : layer) {
                gameObject.draw(graphics);
            }
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
