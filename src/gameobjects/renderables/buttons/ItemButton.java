package gameobjects.renderables.buttons;

import gameobjects.Clickable;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.items.Item;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.screens.InventoryScreen;
import main.utilities.AssetLoader;
import main.utilities.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class ItemButton extends Button{

    private Item item;
    private static String notSelectedImagePath = "/assets/buttons/Button-Inventory-Square.png";
    private static String selectedImagePath = "/assets/buttons/Button-Inventory-Selected.png";
    private BufferedImage notSelectedImage;
    private BufferedImage selectedImage;

    public ItemButton(int x, int y, DrawLayer drawLayer) {
        super(x, y, notSelectedImagePath, drawLayer);
    }

    public ItemButton(int x, int y, DrawLayer drawLayer, Consumer<GameScreen> handleOnClick) {
        super(x, y, notSelectedImagePath, drawLayer, handleOnClick);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void select(){
        Debug.log(true, "Selected");
        setCurrentImage(selectedImage);
    }

    public void deSelect(){
        Debug.log(true, "Deselected");
        setCurrentImage(notSelectedImage);
    }

    @Override
    public void onClick(GameScreen thing) {
        select();
        onClick.accept(thing);
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        if(item != null)
            graphics.drawImage(item.getIcon(), x, y, width, height, null);
    }

    @Override
    public void load() {
        if(image == null){
            image = AssetLoader.load(imagePath);
            notSelectedImage = image;
            selectedImage = AssetLoader.load(selectedImagePath);
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }
}
