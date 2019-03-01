package gameengine.rendering.animation;

import main.utilities.AssetLoader;
import main.utilities.Loadable;

import java.awt.image.BufferedImage;

public class AnimationImage implements Loadable {

    private int displayTime;
    private int frameTimer;
    private String imagePath;
    private BufferedImage image;

    public AnimationImage(String imagePath, int displayTime) {
        this.imagePath = imagePath;
        this.displayTime = displayTime;
        this.frameTimer = displayTime;
    }

    public BufferedImage getImage(){
        return image;
    }

    public void decrementFrameTimer(){
        frameTimer--;
    }

    public int getFrameTimer(){
        return frameTimer;
    }

    public void resetCountdownTime(){
        frameTimer = displayTime;
    }

    @Override
    public void load() {
        image = AssetLoader.load(imagePath);
    }
}
