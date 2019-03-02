package gameengine.rendering.animation;

import main.utilities.Loadable;

import java.util.ArrayList;

public abstract class Animation implements Loadable {

    private String name;

    protected ArrayList<AnimationImage> animationImages = new ArrayList<>();
    protected int frameToDisplay = -1;
    protected int totalFrames = 0;

    public void addAnimationImage(AnimationImage image){
        animationImages.add(image);
        totalFrames++;
    }

    public AnimationImage next(){
        frameToDisplay++;
        if(frameToDisplay >= animationImages.size()){  frameToDisplay = 0; }
        return animationImages.get(frameToDisplay);
    }

    public void load(){
        for (AnimationImage animationImage: animationImages){
            animationImage.load();
        }
    }

    public abstract String getName();
}
