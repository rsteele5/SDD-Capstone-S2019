package view.renderengine;

import control.ScreenManager;
import model.gameobjects.GameObject;
import model.gameobjects.Renderable;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameScreen {

    protected ScreenManager screenManager;

    //region <Variable Declarations>
    private volatile boolean loading = false;
    public boolean isExiting = false;

    protected boolean loadingScreenRequired;

    public boolean isLoading() {
        return loading;
    }
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

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }

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

    //TODO: Consider renaming the Render layers
    /**
     * The background layer contains all the game objects to be rendered to the background.
     */
    protected CopyOnWriteArrayList<Renderable> backgroundLayer;

    /**
     *  The mid background layer contains all the game objects to be rendered that obscure
     *  the background but are still part of the background. Useful for parallax effects.
     */
    protected CopyOnWriteArrayList<Renderable> backgroundMidLayer;

    /**
     *  The foreground layer contains all the game objects to be rendered to the foreground.
     */
    protected CopyOnWriteArrayList<Renderable> foregroundLayer;

    /**
     *  The mid foreground layer contains all the game objects to be rendered that obscure
     *  the foreground but are still part of the foreground. Useful if we want the player
     *  to be obscured when walking through a curtain for instance.
     */
    protected CopyOnWriteArrayList<Renderable> foregroundMidLayer;

    //endregion

    //region<Constructors>
    public GameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        backgroundLayer = new CopyOnWriteArrayList<>();
        backgroundMidLayer = new CopyOnWriteArrayList<>();
        foregroundLayer = new CopyOnWriteArrayList<>();
        foregroundMidLayer = new CopyOnWriteArrayList<>();
    }
    //endregion

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

    /**
     *  Returns true if a screen is active and can accept input or updates
     */
    public boolean isActive(){
        return currentState == ScreenState.Active;
    }

    public abstract void updateTransitionOn();
    public abstract void updateTransitionOff();

    /**
     *  Updates the state of the screen
     */
    public void update(){
        //if state is hidden do stuff
    }

    /**
     *  Draws the screen to the monitor
     */
    public void draw(Graphics2D graphics) {
        for (Renderable gameObject : backgroundLayer) {
            gameObject.draw(graphics);
        }
        for (Renderable gameObject : backgroundMidLayer) {
            gameObject.draw(graphics);
        }
        for (Renderable gameObject : foregroundLayer) {
            gameObject.draw(graphics);
        }
        for (Renderable gameObject : foregroundMidLayer) {
            gameObject.draw(graphics);
        }
    }

    /**
     *  Tells the screen it should exit
     */
    public void exitScreen(){}


    CopyOnWriteArrayList<GameObject> gameObjects;

    public CopyOnWriteArrayList<GameObject> getGameObjects(){
        return gameObjects;
    }

    public void loadContent() {

        loading = true;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            //screenmanager.gimme loading screen
            for(Renderable renderable: foregroundLayer) {
                renderable.loadImages();
            }
            for(Renderable renderable: foregroundMidLayer) {
                renderable.loadImages();
            }
            for(Renderable renderable: backgroundLayer) {
                renderable.loadImages();
            }
            for(Renderable renderable: backgroundMidLayer) {
                renderable.loadImages();
            }
            loading = false;
            //screenmanager. get that shit otta here
            executorService.shutdown();
        });

    }

    public CopyOnWriteArrayList<Renderable> getRenderables() {
        CopyOnWriteArrayList<Renderable> renderables = new CopyOnWriteArrayList<>();
        renderables.addAll(foregroundMidLayer);
        renderables.addAll(foregroundLayer);
        renderables.addAll(backgroundMidLayer);
        renderables.addAll(backgroundLayer);
        return renderables;
    }
}
