package model.gameobjects;

import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class RenderableObject extends GameObject{

    //region <Variables>
    protected BufferedImage currentImage = null;
    protected int drawLayer = 0;
    protected float alpha = 1f;
    protected int width;
    protected int height;
    private ArrayList<String> imagePaths;
    private ArrayList<BufferedImage> images;
    //endregion

    //region <Construction and Initialization>
    public RenderableObject() {
        super();
        currentImage = null;
        width = 0;
        height = 0;
        drawLayer = 0;
    }

    public RenderableObject(int x, int y) {
        super(x,y);
        currentImage = null;
        width = 0;
        height = 0;
        drawLayer = 0;
    }

    public RenderableObject(int x, int y, BufferedImage image, int drawLayer) {
        super(x,y);
        currentImage = image;
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
            for(String path: imagePaths){
                images.add(ImageIO.read(getClass().getResource(path)));
            }
        } catch (IOException exception) {
            Debug.error(DebugEnabler.RENDERABLE_LOG,"Unable to load images");
        }

        currentImage = images.get(0);
    }
    /**
     * This resize method is used to resize a BufferedImage
     * to a new width and heigth.
     * show the usage of various javadoc Tags.
     * It will resize the image associated with the RenderableObject
     * @param newW  This parameter is new width of the image
     * @param newH  This parameter is new heigth of the image
     * @void BufferedImage The resized image takes the place of the original
     */
    /*public void resize(int newW, int newH) {
        Image tmp = currentImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        currentImage = dimg;
    }*/

    public abstract void update();
}
