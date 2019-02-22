package gamescreens.screens.menus.dev.options.controls;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class ControlsScreen extends GameScreen {

    private static String setting = "keyboard";
    private String exitSetting = "keyboard";
    private ImageContainer keyboardImg;
    private ImageContainer gamePadImg;

    //region <Variables>

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    private float alphaTransition = 0.0f;
    //endregion

    //region <Construction and Initialization>
    public ControlsScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen");
        isExclusive = true;
    }

    @Override
    protected void initializeScreen() {

        keyboardImg = new ImageContainer(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/labels/Label-Keyboard.png", DrawLayer.Entity);
        gamePadImg = new ImageContainer(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/labels/Label-Gamepad.png", DrawLayer.Entity);

        Debug.warning(true, setting);
        if(setting.equals("keyboard")){
            addObject(keyboardImg);
            loadables.add(gamePadImg);
        } else {
            addObject(gamePadImg);
            loadables.add(keyboardImg);
        }
        //Create buttons
        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Left Arrow");
                    if(setting.equals("keyboard")){
                        exitSetting = "gamePad";
                        entityLayer.remove(keyboardImg);
                        entityLayer.add(gamePadImg);
                    } else {
                        exitSetting = "keyboard";
                        entityLayer.remove(gamePadImg);
                        entityLayer.add(keyboardImg);
                    }
                }));

        addObject(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Right Arrow");
                    if(setting.equals("keyboard")){
                        exitSetting = "gamePad";
                        entityLayer.remove(keyboardImg);
                        entityLayer.add(gamePadImg);
                    } else {
                        exitSetting = "keyboard";
                        entityLayer.remove(gamePadImg);
                        entityLayer.add(keyboardImg);
                    }
                }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    setting = exitSetting;
                }));

        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    if(!exitSetting.equals(setting)){
                        screenManager.addScreen(new ConfirmControlsPopup(screenManager, this));
                    } else {
                        setScreenState(ScreenState.TransitionOff);
                    }
                }));

        //Create Background on layer 0
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-ControlsMenu.png", DrawLayer.Background));
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
    protected void hiddenUpdate() {
//        if(!screenManager.coveredByOverlay(this))
//            currentState = ScreenState.TransitionOff;
//        else
//            activeUpdate();
    }

    @Override
    protected void activeUpdate() {
//        //Set all labels alpha equal to one, even if they aren't being rendered at the time
//        for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
//            for(RenderableObject renderable : layer)
//                renderable.setAlpha(1.0f);
    }
}
