package view.renderengine;

import control.ScreenManager;
import model.gameobjects.Renderable;
import utilities.Debug;
import utilities.DebugEnabler;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameScreen {

    //region <Variables>
    protected ScreenManager screenManager;
    protected boolean loadingScreenRequired;
    private volatile boolean loading = false;
    //TODO: Maybe consider making one of these a default behavior then set the variable when we want the other behavior
    /**
     *  The variable popup describes if a screen is covering another screen.
     *  A popup screen allows updates on screens below it in the list.
     */
    private boolean popup = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    private boolean exclusivePopup = false;

    /**
     * RederableLayers hold a list of layers that can be rendered.
     * Each index value represents a layer to be rendered to the screen. Layers with
     * higher index values will rendered on top of layers with lower index values.
     */
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<Renderable>> rederableLayers;

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
    //endregion

    //region <Getters and Setters>
    public boolean isLoading() { return loading; }

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
     *  Sets whether a screen is a popup. A popup screen
     *  allows updates on screens below it in the list
     */
    public void setPopup(boolean p) {popup = p;}

    /**
     *  Returns true if a screen is an exclusive popup. An exclusive popup screen
     *  prevents updates on screens below it in the list
     */
    public boolean isExclusivePopup() {return exclusivePopup;}

    /**
     *  Sets whether a screen is an exclusive popup. An exclusive popup screen
     *  prevents updates on screens below it in the list
     */
    public void setExclusivePopup(boolean ep) { exclusivePopup = ep;}
    //endregion

    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        rederableLayers = new CopyOnWriteArrayList<>();
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
     *  Loads the contents of this Game Screen.
     */
    protected abstract void loadContent();

    public CopyOnWriteArrayList<Renderable> getRenderables() {
        CopyOnWriteArrayList<Renderable> renderables = new CopyOnWriteArrayList<>();
        for (CopyOnWriteArrayList<Renderable> layer : rederableLayers) {
            renderables.addAll(layer);
        }
        return renderables;
    }
    //endregion

    //region <Update>
    public abstract void updateTransitionOn();
    public abstract void updateTransitionOff();
    public abstract void hiddenUpdate();

    /**
     *  Updates the state of the screen
     */
    public void update(){
        //if state is hidden do stuff
        switch(currentState) {
            case TransitionOn: updateTransitionOn(); break;
            case TransitionOff: updateTransitionOff(); break;
            case Active: break;
            case Hidden: break;
            default: Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Unknown screen state");
        }
    }
    //endregion

    //region <Render>
    /**
     *  Draws the screen to the monitor
     */
    public void draw(Graphics2D graphics) {
        for (CopyOnWriteArrayList<Renderable> layer : rederableLayers) {
            for (Renderable gameObject : layer) {
                gameObject.draw(graphics);
            }
        }
    }
    //endregion

    //region <Support Functions>
    public abstract void handleClickEvent(int x, int y);
    //endregion
}
