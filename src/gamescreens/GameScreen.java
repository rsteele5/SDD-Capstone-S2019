package gamescreens.screens;

import gamescreens.ScreenManager;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameScreen {

    //region <Variables>
    public String name;
    protected ScreenState previousState = null;
    protected ScreenManager screenManager;
    protected boolean loadingScreenRequired = false;
    private volatile boolean loading;
    //TODO: Maybe consider making one of these a default behavior then set the variable when we want the other behavior
    /**
     *  The variable popup describes if a screen is covering a portion of another screen.
     *  A popup screen allows updates on screens below it in the list.
     */
    protected boolean popup = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    protected boolean exclusivePopup = false;

    /**
     *  The variable overlay describes if a screen is covering another screen in it's entirety, but
     *  does not prevent updates or rendering on screens below it in the list.
     */
    protected boolean overlay = false;

    protected CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    /**
     * RederableLayers hold a list of layers that can be rendered.
     * Each index value represents a layer to be rendered to the screen. Layers with
     * higher index values will rendered on top of layers with lower index values.
     */
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<RenderableObject>> renderableLayers = new CopyOnWriteArrayList<>();


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
    protected ScreenState currentState = ScreenState.TransitionOn;
    protected boolean exiting = false;

    public static final int WIDTH_BUTTON = 256;
    public static final int HEIGHT_BUTTON = 96;
    //endregion

    //region <Getters and Setters>
    public boolean isLoading() { return loading; }

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

    public boolean isExiting(){return exiting;}

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }

    /**
     *  Returns true if a screen is a popup. A popup screen
     *  allows updates on screens below it in the list
     */
    public boolean isPopup() {return popup;}

    /**
     *  Returns true if a screen is an exclusive popup. An exclusive popup screen
     *  prevents updates on screens below it in the list
     */
    public boolean isExclusivePopup() {return exclusivePopup;}

    /**
     *  Returns true if a screen is an overlay. An overlay screen
     *  does not prevent updates on screens below it in the list.
     */
    public boolean isOverlay() {return overlay;}

    //endregion

    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        initializeLayers();

        //Load contents of the screen in a thread
        loading = true;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            loadContent();
            loading = false;
        });
        executorService.shutdown();
    }

    /**
     *  Initializes the renderableLayers array with an arbitrary amount of layers.
     */
    protected abstract void initializeLayers();

    /**
     *  Loads the contents of this main.Game Screen.
     */
    protected abstract void loadContent();

    public CopyOnWriteArrayList<RenderableObject> getRenderables() {
        CopyOnWriteArrayList<RenderableObject> renderableObjects = new CopyOnWriteArrayList<>();
        for (CopyOnWriteArrayList<RenderableObject> layer : renderableLayers) {
            renderableObjects.addAll(layer);
        }
        return renderableObjects;
    }
    //endregion

    //region <Update>
    protected abstract void updateTransitionOn();
    protected abstract void updateTransitionOff();
    protected abstract void hiddenUpdate();
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
            case TransitionOn: updateTransitionOn(); break;
            case TransitionOff: updateTransitionOff(); break;
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
