package gamescreens.screens.menus.dev.options.graphics;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class GraphicsScreen extends GameScreen {

    //region <Variables>

    private static String setting = "high";
    private static String exitSetting = "high";

    private ImageContainer high;
    private ImageContainer medium;
    private ImageContainer low;


    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private float alphaTransition = 0.0f;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public GraphicsScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen");
        isExclusive = true;
    }
    @Override
    protected void initializeScreen() {

        //Create Background on layer 0
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-GraphicsMenu.png", DrawLayer.Background));

        high = new ImageContainer(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/labels/Label-High.png", DrawLayer.Entity);
        medium = new ImageContainer(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/labels/Label-Medium.png", DrawLayer.Entity);
        low = new ImageContainer(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/labels/Label-Low.png", DrawLayer.Entity);

        if(setting.equals("low")){
            addObject(low);
            loadables.add(high);
            loadables.add(medium);
        } else if (setting.equals("medium")){
            addObject(medium);
            loadables.add(low);
            loadables.add(high);
        } else {
            addObject(high);
            loadables.add(medium);
            loadables.add(low);
        }

        //Create buttons
        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-LeftArrow.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Left Arrow" + setting);
                    if(exitSetting.equals("high")){
                        exitSetting = "medium";
                        entityLayer.remove(high);
                        entityLayer.add(medium);
                    } else if (exitSetting.equals("medium")){
                        exitSetting = "low";
                        entityLayer.remove(medium);
                        entityLayer.add(low);
                    } else {
                        exitSetting = "high";
                        entityLayer.remove(low);
                        entityLayer.add(high);
                    }
                }));

        addObject(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-RightArrow.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Right Arrow");
                    if(exitSetting.equals("low")){
                        exitSetting = "medium";
                        entityLayer.remove(low);
                        entityLayer.add(medium);
                    } else if (exitSetting.equals("medium")){
                        exitSetting = "high";
                        entityLayer.remove(medium);
                        entityLayer.add(high);
                    } else {
                        exitSetting = "low";
                        entityLayer.remove(high);
                        entityLayer.add(low);
                    }
                }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Confirm");
                    setScreenState(ScreenState.TransitionOff);
                    setting = exitSetting;
                }));

        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    if(!exitSetting.equals(setting)){
                        screenManager.addScreen(new ConfirmGraphicsPopup(screenManager, this));
                    } else {
                        this.setScreenState(ScreenState.TransitionOff);
                    }
                }));


    }

    //endregion

    @Override
    protected void transitionOn() {
        float alpha = alphaTransition;
        if(alpha < 0.9f){
            alphaTransition += 0.05f;
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(alphaTransition);
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

    @Override
    protected void transitionOff() {
        float alpha = alphaTransition;
        if(alpha > 0.055f){
            alphaTransition -= 0.05f;
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(alphaTransition);
        } else {
            exiting = true;
        }
    }

    @Override
    protected void activeUpdate() {
        //Set all labels alpha equal to one, even if they aren't being rendered at the time
        for(RenderableObject renderable : entityLayer)
            renderable.setAlpha(1.0f);
    }
}
