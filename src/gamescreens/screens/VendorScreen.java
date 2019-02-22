package gamescreens.screens;

import gameengine.rendering.RenderEngine;
import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.items.Helmet;
import gameobjects.renderables.items.Item;
import gameobjects.renderables.items.Potion;
import gameobjects.renderables.items.Sword;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {
    /* Array of x values for bear item box locations **/
    private int [] xValBearItems = {191, 239, 287, 335};

    /* Array of x values for vendor item box locations **/
    private int [] xValVendorItems = {927, 975, 1023, 1071};

    /* Array of y values for bear AND vendor item box locations **/
    private int [] yValItems = {220, 266, 313, 360, 407, 455, 502, 549};

    /* x and y positions for text */
    private int x_position = 765;
    private int y_position = 220;

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
        addObject(new ImageContainer(765, 410, "/assets/Vendor.png", DrawLayer.Entity));
        addObject(new ImageContainer(400, 380, "/assets/Teddy.png", DrawLayer.Entity));

        /* Create buttons **/
        addObject(new Button(175, 100,
                "/assets/buttons/Button-Vendor-Exit.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                })));
        addObject(new Button(775, 560,
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

        addObject(new Button(400, 560,
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

        bearInventory.add(new Sword());
        vendorInventory.add(new Potion());
        vendorInventory.add(new Potion());
        vendorInventory.add(new Sword());
        vendorInventory.add(new Helmet());
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
        if (currentItem != null){
            graphics.setColor(Color.BLACK);
            graphics.drawString(currentItem.getItemName(), x_position, y_position);
            graphics.drawString("Type: " + currentItem.getType(), x_position, y_position += 20);
            graphics.drawString("Damage: " + currentItem.getDamage(), x_position, y_position += 20);
            graphics.drawString("Immunity: " + currentItem.getImmunity(), x_position, y_position += 20);
            graphics.drawString("CritChance: " + currentItem.getCritChance() + "%", x_position, y_position += 20);
            graphics.drawString("Value: $" + currentItem.getValue(), x_position, y_position += 20);
            graphics.drawString(currentItem.getDescription1(), x_position, y_position += 30);
            if (currentItem.getDescription2() != null) {
                graphics.drawString(currentItem.getDescription2(), x_position, y_position += 20);
            }
        }
        // reset y_position for next item description
        y_position = 220;
    }

    private void createItemButtons(){
        int [] xValBearItems = {191, 239, 287, 335};
        int [] xValVendorItems = {927, 975, 1023, 1071};
        int [] yValItems = {220, 266, 313, 360, 407, 455, 502, 549};

        /* Render item images (buttons) into bear's inventory **/
        int itemCount = bearInventory.size();
        int k = 0;
        for (int yValItem1 : yValItems) {
            for (int xValBearItem : xValBearItems) {
                if (k < itemCount) {
                    Item myItem = bearInventory.get(k);
                    Button itemButton = new Button(xValBearItem, yValItem1, myItem.getImagePath(), DrawLayer.Entity);
                    itemButton.onClick = (screenManager -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
                        currentItem = myItem;
                        currentButton = itemButton;
                        // Adjust where text is rendered
                        x_position = 400;
                    });
                    addObject(itemButton);
//                    addObject(new Button(xValBearItem, yValItem1, myItem.getImagePath(), DrawLayer.Entity,
//                            (screenManager -> {
//                                Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
//                                currentItem = myItem;
//                                // Adjust where text is rendered
//                                x_position = 400;
//                            })));
                    k++;
                }
            }
        }
        /* Render item images (buttons) into vendor's inventory **/
        itemCount = vendorInventory.size();
        k = 0;
        for (int yValItem : yValItems) {
            for (int xValVendorItem : xValVendorItems) {
                if (k < itemCount) {
                    Item myItem = vendorInventory.get(k);
                    Debug.log(true, myItem.getImagePath());
                    Button itemButton = new Button(xValVendorItem, yValItem, myItem.getImagePath(), DrawLayer.Entity);
                    itemButton.onClick = (screenManager -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
                        currentItem = myItem;
                        currentButton = itemButton;
                        // Adjust where text is rendered
                        x_position = 400;
                    });
                    addObject(itemButton);
//                    addObject(new Button(xValVendorItem, yValItem, myItem.getImagePath(), DrawLayer.Entity,
//                            (screenManager -> {
//                                Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor Item");
//                                currentItem = myItem;
//                                // Adjust where text is rendered
//                                x_position = 765;
//                            })));
                    k++;
                }
            }
        }
    }
}
