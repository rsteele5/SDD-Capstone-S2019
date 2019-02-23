package gamescreens.screens;

import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.items.Weapon;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.items.Armor;
import gameobjects.renderables.items.Item;
import gameobjects.renderables.items.Consumable;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {

    /* x and y positions for text */
    private int x_position = 765;
    private int y_position = 200;

    private Item currentItem = null;
    private Button currentButton = null;
    /* ****************************************/

    /** Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<Item> bearInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;


    public VendorScreen(ScreenManager screenManager) {
        super(screenManager, "VendorScreen");
        isExclusive = true;

        /* Remove after testing. Populates inventories with red potion **/
     }

    @Override
    protected void initializeScreen() {
        bearInventory = new CopyOnWriteArrayList<>();
        vendorInventory = new CopyOnWriteArrayList<>();
        /* Create all renderables **/
        addObject(new ImageContainer(150, 75, "/assets/VendorBackground.png", DrawLayer.Background));
        addObject(new ImageContainer(720, 410, "/assets/Vendor.png", DrawLayer.Entity));
        addObject(new ImageContainer(445, 400, "/assets/VendorTeddy.png", DrawLayer.Entity));

        /* Create buttons **/
        addObject(new Button(175, 100,
                "/assets/buttons/Button-Vendor-Exit.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                })));
        addObject(new Button(735, 560,
                "/assets/buttons/Button-Vendor-Buy.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        vendorInventory.remove(currentItem);
                        bearInventory.add(currentItem);
                        currentItem = null;
                    }
                })));

        addObject(new Button(455, 560,
                "/assets/buttons/Button-Vendor-Sell.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    if (bearInventory.size() > 0 && currentItem != null) {
                        bearInventory.remove(currentItem);
                        vendorInventory.add(currentItem);
                        currentItem = null;
                    }
                })));

        bearInventory.add(new Weapon("/assets/Items/swordSmall.png", "Sword", "Weapon", "It's a sword"));
        vendorInventory.add(new Consumable("/assets/Items/redPotionSmall.png", "Fire Potion", "Potion", "It's a potion"));
        vendorInventory.add(new Consumable("/assets/Items/redPotionSmall.png", "Fire Potion", "Potion", "It's a potion"));
        vendorInventory.add(new Weapon("/assets/Items/swordSmall.png", "Sword", "Weapon", "It's a sword"));
        vendorInventory.add(new Armor("/assets/Items/helmetSmall.png", "Helmet", "Armor", "It's a helmet"));
        createItemButtons();
    }

    @Override
    protected void transitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }

    @Override
    public void handleClickEvent(int x, int y) {
        for(Button butt: buttons) {
            if(butt.getBoundingBox().contains(x,y)) {
                butt.onClick.accept(screenManager);
                return;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        // Call this method to draw a string to the screen
        if (currentItem != null) {
            currentItem.drawMe(graphics, x_position, y_position, false, 28);
        }

        // reset y_position for next item description
        y_position = 200;
    }

    private void createItemButtons(){
        int xValBearItems = 191;
        int xb = xValBearItems;
        int xValVendorItems = 902;
        int xv = xValVendorItems;
        int yValItems = 219;
        int y = yValItems;

        /* Render item images (buttons) into bear's inventory **/
        int itemCount = bearInventory.size();
        int k = 0;
        int rows = 8;
        int columns = 5;
        int space = 48;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (k < itemCount) {
                    Item myItem = bearInventory.get(k);
                    Button itemButton = new Button(xb, y, myItem.getImagePath(), DrawLayer.Entity);
                    itemButton.onClick = (screenManager -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
                        currentItem = myItem;
                        currentButton = itemButton;
                        // Adjust where text is rendered
                        x_position = 400;
                    });
                    addObject(itemButton);
                    xb += space;
                    k++;
                }
            }
            xb = xValBearItems;
            y += space;
        }
        y = yValItems;

        /* Render item images (buttons) into vendor's inventory **/
        itemCount = vendorInventory.size();
        k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (k < itemCount) {
                    Item myItem = vendorInventory.get(k);
                    Button itemButton = new Button(xv, y, myItem.getImagePath(), DrawLayer.Entity);
                    itemButton.onClick = (screenManager -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor Item");
                        currentItem = myItem;
                        currentButton = itemButton;
                        // Adjust where text is rendered
                        x_position = 765;
                    });
                    addObject(itemButton);
                    xv += space;
                    k++;
                }
            }
            xv = xValVendorItems;
            y += space;
        }
        y = yValItems;
    }
}
