package gameobjects.renderables.buttons;

import gameobjects.renderables.items.Item;
import gamescreens.DrawLayer;
import main.utilities.Action;
import main.utilities.AssetLoader;
import main.utilities.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ItemButton extends Button{

    private Item item;
    private static String notSelectedImagePath = "/assets/buttons/Button-Inventory-Square.png";
    private static String selectedImagePath = "/assets/buttons/Button-Inventory-Selected.png";
    private BufferedImage notSelectedImage;
    private BufferedImage selectedImage;

    public ItemButton(int x, int y, DrawLayer drawLayer) {
        super(x, y, notSelectedImagePath, drawLayer);
    }

    public ItemButton() {
        this(0, 0, DrawLayer.Entity);
    }

    public ItemButton(int x, int y, DrawLayer drawLayer, Action handleOnClick) {
        super(x, y, notSelectedImagePath, drawLayer, handleOnClick);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void resetItem() {this.item = null;}

    public void select(){
        Debug.log(true, "Selected");
        setCurrentImage(selectedImage);
    }

    @Override
    public void setCurrentImage(BufferedImage image) {
        this.image = image;
    }

    public void deSelect(){
        Debug.log(true, "Deselected");
        setCurrentImage(notSelectedImage);
    }

    @Override
    public void onClick() {
        select();
        if(onClick != null){
            onClick.doIt();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        //If the image is not null draw it offset in the center of the button
        if(item != null)
            graphics.drawImage(item.getIcon(), x +7, y + 7, width -14, height -14, null);
    }

    @Override
    public void load() {
        if(image == null){
            image = AssetLoader.load(imagePath);
            notSelectedImage = image;
            selectedImage = AssetLoader.load(selectedImagePath);
            if(width == 0 && height == 0) {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }
}
