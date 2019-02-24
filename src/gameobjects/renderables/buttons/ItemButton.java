package gameobjects.renderables.buttons;

import gameobjects.Clickable;
import gameobjects.renderables.items.Item;
import gamescreens.DrawLayer;
import gamescreens.ScreenManager;
import main.utilities.AssetLoader;
import main.utilities.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class ItemButton extends Button implements Clickable {

    private Item item;
    private static String notSelectedImagePath = "/assets/buttons/Button-Inventory-Square.png";
    private static String selectedImagePath = "/assets/buttons/Button-Inventory-Selected.png";
    private BufferedImage selectedImage;


    public ItemButton(int x, int y, DrawLayer drawLayer) {
        super(x, y, notSelectedImagePath, drawLayer);
    }

    public ItemButton(int x, int y, DrawLayer drawLayer, Consumer<ScreenManager> handleOnClick) {
        super(x, y, notSelectedImagePath, drawLayer, handleOnClick);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setSelectedImage(){
        Debug.log(true, "Set image");
        setCurrentImage(selectedImage);
    }

    @Override
    public void onClick(ScreenManager screenManager) {
        super.onClick(screenManager);
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        Debug.drawRect(true, graphics, getBoundingBox());
        if(item != null)
            graphics.drawImage(item.getIcon(), x, y, width, height, null);
    }

    @Override
    public void load() {
        if(image == null){
            image = AssetLoader.load(imagePath);
            selectedImage = AssetLoader.load(selectedImagePath);
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }
}
