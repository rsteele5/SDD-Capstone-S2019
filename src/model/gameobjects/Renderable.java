package model.gameobjects;

import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Renderable extends GameObject{

    protected BufferedImage currentImage = null;
    protected int drawLayer = 0;
    protected float alpha = 1f;
    private ArrayList<String> imagePaths;
    private ArrayList<BufferedImage> images;

    public Renderable() {
        super();
    }

    public Renderable(int x, int y) {
        super(x,y);
    }

    public Renderable(int x, int y, BufferedImage image, int drawLayer) {
        super(x,y);
        currentImage = image;
        this.drawLayer = drawLayer;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }

    public int getDrawLayer() {
        return drawLayer;
    }

    public void setDrawLayer(int drawLayer) {
        this.drawLayer = drawLayer;
    }

    public void draw(Graphics2D graphics) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        graphics.drawImage(currentImage, x, y, null);
    }

    public void loadImages() {
        try{
            for(String path: imagePaths){
                images.add(ImageIO.read(getClass().getResource(path)));
            }
        } catch (IOException exception) {
            Debug.error(DebugEnabler.RENDERABLE_LOG,"Unable to load images");
        }

        currentImage = images.get(0);
    }

    public abstract void update();

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(x, y, currentImage.getWidth(), currentImage.getHeight());
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    public float getAlpha() {
        return alpha;
    }
}
