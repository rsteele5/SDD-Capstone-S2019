package gameobjects.renderables;

import gameobjects.GameObject;
import gamescreens.DrawLayer;
import main.utilities.AssetLoader;
import main.utilities.Loadable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class RenderableObject extends GameObject implements Loadable {

    //region <Variables>
    protected DrawLayer drawLayer;
    protected float alpha = 1f;
    protected int width;
    protected int height;
    protected String imagePath;
    protected BufferedImage image;
    //endregion

    //region <Construction and Initialization>
    public RenderableObject() {
        super();
        image = null;
        imagePath = "";
        width = 0;
        height = 0;
        drawLayer = DrawLayer.Background;
    }

    public RenderableObject(int x, int y) {
        super(x,y);
        image = null;
        imagePath = "";
        width = 0;
        height = 0;
        drawLayer = DrawLayer.Background;
    }

    public RenderableObject(int x, int y, DrawLayer layer) {
        super(x,y);
        this.imagePath = imagePath;
        drawLayer = layer;
    }

    public RenderableObject(int x, int y, BufferedImage image, DrawLayer layer) {
        super(x,y);
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        drawLayer = layer;
    }

    public RenderableObject(int x, int y, String imagePath, DrawLayer layer) {
        super(x,y);
        this.imagePath = imagePath;
        drawLayer = layer;
    }

    public RenderableObject(int x, int y, String imagePath, DrawLayer layer, float alpha) {
        super(x,y);
        this.imagePath = imagePath;
        this.drawLayer = layer;
        this.alpha = alpha;
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
        this.image = currentImage;
        width = currentImage.getWidth();
        height = currentImage.getHeight();
    }

    public DrawLayer getDrawLayer() {
        return drawLayer;
    }

    public void setDrawLayer(DrawLayer drawLayer) {
        this.drawLayer = drawLayer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public BufferedImage getCurrentImage() {
        return image;
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
        graphics.drawImage(image, x , y, width, height, null);
    }

    public void load() {
        if(image == null){
            image = AssetLoader.load(imagePath);
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }

    public abstract void update();
}
