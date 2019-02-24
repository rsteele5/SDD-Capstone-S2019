package gameobjects.renderables.buttons;

import gameobjects.Clickable;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.items.Item;
import gamescreens.DrawLayer;
import gamescreens.ScreenManager;
import gamescreens.screens.InventoryScreen;
import main.utilities.AssetLoader;
import main.utilities.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class ItemButton extends RenderableObject implements Clickable<ItemButton> {

    private Item item;
    private static String notSelectedImagePath = "/assets/buttons/Button-Inventory-Square.png";
    private static String selectedImagePath = "/assets/buttons/Button-Inventory-Selected.png";
    private BufferedImage notSelectedImage;
    private BufferedImage selectedImage;

    public Consumer<ItemButton> onClick;

    public ItemButton(int x, int y, DrawLayer drawLayer) {
        super(x, y, notSelectedImagePath, drawLayer);
    }

    public ItemButton(int x, int y, DrawLayer drawLayer, Consumer<ItemButton> handleOnClick) {
        super(x, y, notSelectedImagePath, drawLayer);
        this.onClick = handleOnClick;
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
    public void setOnClick(Consumer<ItemButton> onClick) {
        this.onClick = onClick;
    }

    @Override
    public boolean contains(int x, int y) {
        return getBoundingBox().contains(x,y);
    }

    @Override
    public void onClick(ItemButton itemButton) {
        onClick.accept(itemButton);
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

    @Override
    public void update() {

    }
}
