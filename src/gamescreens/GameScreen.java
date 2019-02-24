package gamescreens;

import gameengine.physics.Kinematic;
import gameobjects.Clickable;
import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import gamescreens.screens.LoadingScreen;
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

    protected int x,y;

    private GameScreen childScreen;
    private GameScreen overlayScreen;
    public LoadingScreen loadingScreen;

    //TODO: Used for testing, remove after screen management is working. If it so tickles your pickles
    protected ScreenState previousState;

    protected ScreenManager screenManager;

    protected boolean loadingScreenRequired = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    protected boolean isExclusive = false;
    protected boolean isOverlay = false;

    /**
     *  The variable exclusivePopup describes if a screen is covering a portion of another screen.
     *  An exclusive popup screen prevents updates on screens below it in the list.
     */
    private boolean isLoading;

    /**
     *  The variable overlay describes if a screen is covering another screen in it's entirety, but
     *  does not prevent updates or rendering on screens below it in the list.
     */
    protected boolean isRoot;

    protected boolean exiting = false;

    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<Clickable> clickables;
    protected ArrayList<Kinematic> kinematics;
    protected ArrayList<Loadable> loadables;
    protected ArrayList<RenderableObject> backgroundLayer;
    protected ArrayList<RenderableObject> sceneryLayer;
    protected ArrayList<RenderableObject> entityLayer;
    protected ArrayList<RenderableObject> effectsLayer;

    public void coverWith(GameScreen gameScreen) {
        if(gameScreen.isRoot) {
            gameScreen.childScreen = this;
            currentState = ScreenState.TransitionOff;
            return;
        }
        if(gameScreen.isExclusive) {
            if (childScreen == null) {
                childScreen = gameScreen;
            } else {
                childScreen.coverWith(gameScreen);
            }
        } else {
            childScreen.overlayScreen = this;
        }
    }

    public void uncoveredBy(GameScreen gameScreen) {
        if(childScreen == gameScreen) {
            if(childScreen.childScreen != null) {
                childScreen = childScreen.childScreen;
            } else {
                childScreen = null;
            }
        }
    }


    public ArrayList<Kinematic> getPhysicsObjects() {
        if(!isLoading){
            if(childScreen != null) {
                return childScreen.getPhysicsObjects();
            } else {
                return kinematics;
            }
        } else {
            return null;
        }
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
    protected ScreenState currentState;
    //endregion

    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager, String name) {
        this.screenManager = screenManager;
        previousState = null;
        gameObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        backgroundLayer = new ArrayList<>();
        sceneryLayer = new ArrayList<>();
        entityLayer = new ArrayList<>();
        effectsLayer = new ArrayList<>();
        x = 0;
        y = 0;
        this.name = name;
        this.isRoot = true;
        initializeScreen();
        currentState = ScreenState.TransitionOn;
        isLoading = true;
        loadContent();
    }


    /* Only for non root screen */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive) {
        this(screenManager, name, isExclusive, 0,0);
    }

    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos) {
        this.screenManager = screenManager;
        previousState = null;
        gameObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        backgroundLayer = new ArrayList<>();
        sceneryLayer = new ArrayList<>();
        entityLayer = new ArrayList<>();
        effectsLayer = new ArrayList<>();
        this.name = name;
        this.isRoot = false;
        x = xPos;
        y = yPos;
        this.isExclusive = isExclusive;
        this.isOverlay = !isExclusive;
        initializeScreen();
        currentState = ScreenState.TransitionOn;
        isLoading = true;
        loadContent();

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
            if(loadingScreenRequired) {
                loadingScreen = screenManager.getLoadingScreen();
                loadingScreen.initializeLoadingScreen(loadables.size());
                coverWith(loadingScreen);
                for (int i = 0; i < loadables.size(); i++){
                    loadables.get(i).load();
                    loadingScreen.dataLoaded(i);
                }
                isLoading = false;
                childScreen = null;
                loadingScreen.reset();
            }
            for (Loadable loadable : loadables) loadable.load();
            loadables.removeAll(loadables);
            isLoading = false;
        });
        executorService.shutdown();
    }
    //endregion

    //region <Getters and Setters>
    public int getX(){return x;}
    public int getY(){return y;}

    public void setPosition(int xPos, int yPos){
        x = xPos;
        y = yPos;
    }

    public boolean isLoadingScreenRequired(){
        return loadingScreenRequired;
    }

    public boolean isExclusive(){
        return isExclusive;
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


    public final void drawScreen(Graphics2D graphics) {
        if(!isLoading) {
            drawLayers(graphics);
            if(childScreen != null) {
                childScreen.drawScreen(graphics);
            }
            if(overlayScreen != null) {
                overlayScreen.drawScreen(graphics);
            }
        } else {
            if(childScreen != null) {
                childScreen.drawScreen(graphics);
            }
        }
    }

    private void drawLayers(Graphics2D graphics) {
        for(RenderableObject bg : backgroundLayer)
            bg.draw(graphics);
        for(RenderableObject scenery : sceneryLayer)
            scenery.draw(graphics);
        for(RenderableObject entity : entityLayer)
            entity.draw(graphics);
        for(RenderableObject effect : effectsLayer)
            effect.draw(graphics);
    }

    /**
     *  Updates the state of the screen
     */
    public void update(){
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
            if(currentState != previousState){
                previousState = currentState;
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-CurrentState: " + currentState.name());
            }
        }
    }
    //endregion

    //region <Support Functions>
    public void handleClickEvent(int x, int y) {

        if(childScreen != null) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle click on child");
            childScreen.handleClickEvent(x,y);
        } else {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-screen handle click ");
            for(Clickable thing: clickables) {
                if(thing.contains(x,y)) {
                    //TODO: Need To Do type check for button or item button
                    thing.onClick(this);
                    return;
                }
            }
            if(overlayScreen != null){
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle click on overlay");
                overlayScreen.handleClickEvent(x,y);
            }
        }
    }

    public void reset(){
        previousState = null;
        exiting = false;
    }

    public void addObject(RenderableObject renderable) {
        renderable.setX(x + renderable.getX());
        renderable.setY(y + renderable.getY());
        if(renderable instanceof Kinematic)
            kinematics.add((Kinematic) renderable);
        if(renderable instanceof Clickable)
            clickables.add((Clickable) renderable);

        switch (renderable.getDrawLayer()){
            case Background: backgroundLayer.add(renderable);break;
            case Scenery: sceneryLayer.add(renderable);break;
            case Entity: entityLayer.add(renderable);break;
            case Effects: effectsLayer.add(renderable);break;
        }
        loadables.add(renderable);
        gameObjects.add(renderable);
    }

    public void defaultTransitionOn() {
        float alpha = backgroundLayer.get(0).getAlpha();
        if(alpha < 0.9f){
            alpha += 0.05f;
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(alpha);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(alpha);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(alpha);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(alpha);
        } else {
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(1.0f);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(1.0f);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(1.0f);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(1.0f);
            currentState = ScreenState.Active;
        }
    }

    public void defaultTransitionOff() {
        float alpha = backgroundLayer.get(0).getAlpha();
        if(alpha > 0.055f){
            alpha -= 0.05f;
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(alpha);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(alpha);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(alpha);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(alpha);
        } else {
            exiting = true;
        }
    }
    //endregion
}
