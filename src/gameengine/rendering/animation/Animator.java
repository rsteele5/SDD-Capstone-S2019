package gameengine.rendering.animation;

import gameobjects.renderables.RenderableObject;
import main.utilities.Loadable;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Animator implements Loadable {

    private final RenderableObject renderableObject;
    private final HashMap<String, Animation> animations = new HashMap<>();
    private Animation activeAnimation;
    private AnimationImage displayImage;

    public Animator(RenderableObject renderableObject){
        this.renderableObject = renderableObject;
    }

    public final BufferedImage getDisplayImage(String string){
        return displayImage.getImage();
    }

    public final void setAnimation(String string){
        activeAnimation = animations.get(string);
        displayImage = activeAnimation.next();
    }

    public final void addAnimation(String name, Animation image){
        animations.put(name, image);
    }

    public void animate(){
        //Debug.log(true, "Animate!");
        if(displayImage.getFrameTimer() > 0){
            displayImage.decrementFrameTimer();
        }
        else{
            displayImage.resetCountdownTime();
            displayImage = activeAnimation.next();
            renderableObject.setCurrentImage(displayImage.getImage());
        }
    }

    @Override
    public void load() {
        for(HashMap.Entry<String, Animation> entry : animations.entrySet()) {
            Animation animation = entry.getValue();
            animation.load();
        }
    }
}
