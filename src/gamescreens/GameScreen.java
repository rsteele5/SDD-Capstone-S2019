package gamescreens;

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

    private ScreenData screenData;

    protected ScreenState previousState;

    protected ScreenManager screenManager;

    protected boolean loadingScreenRequired = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    public boolean isExclusive = false;

    /**
     *  The variable overlay describes if a screen is covering another screen in it's entirety, but
     *  does not prevent updates or rendering on screens below it in the list.
     */
    public boolean isOverlay = false;

    public boolean exiting = false;


    /**
     *  <p>Screen state describes all possible states that a screen can be in:</p>
     *  <p><b>TransitionOn</b> - The screen is currently undergoing transition on effects. ie fade in etc</p>
     *  <p><b>Active</b> - The screen is currently active and can accept input and update its objects</p>
     *  <p><b>TransitionOff</b> - The screen is currently undergoing transition off effects. ie fade in etc</p>
     *  <p><b>Hidden</b> - The screen is currently covered by another screen</p>
     */
    public enum ScreenState{
        Loading,
        TransitionOn,
        Active,
        TransitionOff,
        Hidden
    }

    /**
     *  Current state describes what state the screen is currently in.
     */
    protected ScreenState currentState = ScreenState.TransitionOn;


    public static final int WIDTH_BUTTON = 256;
    public static final int HEIGHT_BUTTON = 96;
    //endregion

    //region <Getters and Setters>
    public boolean isLoadingScreenRequired(){
        return loadingScreenRequired;
    }

    public boolean isLoading() { return currentState == ScreenState.Loading; }

    /**
     *  Returns true if a screen is active and can accept input or updates
     */
    public boolean isActive(){
        return currentState == ScreenState.Active;
    }

    public boolean isHidden(){return currentState == ScreenState.Hidden;}

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

        //Load contents of the screen in a thread
        currentState = ScreenState.Loading;
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
    private void initializeScreen() {
        previousState = null;
    }

    /**
     *  Loads the contents of this main.Game Screen.
     */
    protected void loadContent() {
        for(RenderableObject renderable: screenData.gameObjects)
    }

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
