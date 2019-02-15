package model.gameobjects.renderable;

import control.RenderEngine;
import model.gameobjects.GameObject;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class RenderableObject extends GameObject {

    //region <Variables>
    protected BufferedImage currentImage = null;
    protected int drawLayer = 0;
    protected float alpha = 1f;
    protected int width;
    protected int height;
    private String imagePath;
    //endregion

    //region <Construction and Initialization>
    public RenderableObject(String imagePath, int drawLayer) {
        super();
        this.imagePath = imagePath;
        this.drawLayer = drawLayer;
    }

    public RenderableObject(int x, int y, String imagePath, int drawLayer) {
        super(x,y);
        this.imagePath = imagePath;
        this.drawLayer = drawLayer;
    }

    public RenderableObject(int x, int y, BufferedImage image, int drawLayer) {
        super(x,y);
        this.imagePath = "/assets/MissingImage.png";
        this.currentImage = image;
        width = currentImage.getWidth();
        height = currentImage.getHeight();
        this.drawLayer = drawLayer;
    }

    public RenderableObject(int x, int y, String imagePath, BufferedImage image, int drawLayer) {
        super(x,y);
        this.imagePath = imagePath;
        this.currentImage = image;
        width = currentImage.getWidth();
        height = currentImage.getHeight();
        this.drawLayer = drawLayer;
    }
    //endregion

    //region <Getters and Setters>
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    public float getAlpha() {
        return alpha;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
        width = currentImage.getWidth();
        height = currentImage.getHeight();
    }

    public int getDrawLayer() {
        return drawLayer;
    }

    public void setDrawLayer(int drawLayer) {
        this.drawLayer = drawLayer;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public void setWidth(int w){
        width = w;
    }

    public void setHeight(int h){
        height = h;
    }

    public void setSize(int w, int h){
        width = w;
        height = h;
    }
    //endregion

    public void draw(Graphics2D graphics) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        graphics.drawImage(currentImage, x, y, width, height, null);
    }

    public void loadImages() {
        try{
            currentImage = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource(imagePath)));
            this.width = currentImage.getWidth();
            this.height = currentImage.getHeight();
        } catch (IOException exception) {
            Debug.error(DebugEnabler.RENDERABLE_LOG,"Unable to load images");
        }
    }

    public abstract void update();
}
