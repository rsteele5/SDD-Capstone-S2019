package gamescreens.screens;

import gameobjects.Clickable;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.Vendor;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.items.Item;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {
    /* Variables ******************************/
    private int x_screen = 100;
    private int y_screen = 100;


    /* x and y positions for text */
    private int x_playerText = 300;
    private int x_vendorText = 665;
    private int y_position = 220;

    private Item currentItem = null;
    /* ****************************************/

    /** Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<Item> bearInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;



    public VendorScreen(ScreenManager screenManager) {
        super(screenManager, "VendorScreen", true);

        /* Remove after testing. Populates inventories with red potion **/
     }

    @Override
    protected void initializeScreen() {
        bearInventory = new CopyOnWriteArrayList<>();
        vendorInventory = new CopyOnWriteArrayList<>();
        /* Create all renderables **/
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(150, 75, "/assets/VendorBackground.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(765, 410, "/assets/Vendor.png", DrawLayer.Entity);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(400, 380, "/assets/Teddy.png", DrawLayer.Entity);
        imageContainer.addToScreen(this, true);


        /* Create buttons **/
        Button button;

        button = new Button(175, 100,
                "/assets/buttons/Button-Vendor-Exit.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                }));
        button.addToScreen(this, true);

        button = new Button(775, 560,
                "/assets/buttons/Button-Vendor-Buy.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        vendorInventory.remove(currentItem);
                        bearInventory.add(currentItem);
                        currentItem = null;
                    }
                }));
        button.addToScreen(this, true);

        button = new Button(400, 560,
                "/assets/buttons/Button-Vendor-Sell.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    if (bearInventory.size() > 0 && currentItem != null) {
                        bearInventory.remove(currentItem);
                        vendorInventory.add(currentItem);
                        currentItem = null;
                    }
                }));
        button.addToScreen(this, true);


//        bearInventory.add(new Weapon());
//        vendorInventory.add(new Potion());
//        vendorInventory.add(new Potion());
//        vendorInventory.add(new Weapon());
//        vendorInventory.add(new Helmet());
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

//    @Override
//    public void draw(Graphics2D graphics) {
//        // Call this method to draw a string to the screen
//        if (currentItem != null){
//            graphics.setColor(Color.BLACK);
//            graphics.drawString(currentItem.getItemName(), x_position, y_position);
//            graphics.drawString("Type: " + currentItem.getType(), x_position, y_position += 20);
//            graphics.drawString("Damage: " + currentItem.getDamage(), x_position, y_position += 20);
//            graphics.drawString("Immunity: " + currentItem.getImmunity(), x_position, y_position += 20);
//            graphics.drawString("CritChance: " + currentItem.getCritChance() + "%", x_position, y_position += 20);
//            graphics.drawString("Value: $" + currentItem.getValue(), x_position, y_position += 20);
//            graphics.drawString(currentItem.getDescription1(), x_position, y_position += 30);
//            if (currentItem.getDescription2() != null) {
//                graphics.drawString(currentItem.getDescription2(), x_position, y_position += 20);
//            }
//        }
//        // reset y_position for next item description
//        y_position = 220;
//    }

    private void createItemButtons(){
//        int [] xValBearItems = {191, 239, 287, 335};
//        int [] xValVendorItems = {927, 975, 1023, 1071};
//        int [] yValItems = {220, 266, 313, 360, 407, 455, 502, 549};
//
//        /* Render item images (buttons) into bear's inventory **/
//        int itemCount = bearInventory.size();
//        int k = 0;
//        for (int yValItem1 : yValItems) {
//            for (int xValBearItem : xValBearItems) {
//                if (k < itemCount) {
//                    Item myItem = bearInventory.get(k);
//                    Button itemButton = new Button(xValBearItem, yValItem1, myItem.getImagePath(), DrawLayer.Entity);
//                    itemButton.onClick = (screenManager -> {
//                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
//                        currentItem = myItem;
//                        currentButton = itemButton;
//                        // Adjust where text is rendered
//                        x_position = 400;
//                    });
//                    addObject(itemButton);
////                    addObject(new Button(xValBearItem, yValItem1, myItem.getImagePath(), DrawLayer.Entity,
////                            (screenManager -> {
////                                Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
////                                currentItem = myItem;
////                                // Adjust where text is rendered
////                                x_position = 400;
////                            })));
//                    k++;
//                }
//            }
//        }
//        /* Render item images (buttons) into vendor's inventory **/
//        itemCount = vendorInventory.size();
//        k = 0;
//        for (int yValItem : yValItems) {
//            for (int xValVendorItem : xValVendorItems) {
//                if (k < itemCount) {
//                    Item myItem = vendorInventory.get(k);
//                    Debug.log(true, myItem.getImagePath());
//                    Button itemButton = new Button(xValVendorItem, yValItem, myItem.getImagePath(), DrawLayer.Entity);
//                    itemButton.onClick = (screenManager -> {
//                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
//                        currentItem = myItem;
//                        currentButton = itemButton;
//                        // Adjust where text is rendered
//                        x_position = 400;
//                    });
//                    addObject(itemButton);
////                    addObject(new Button(xValVendorItem, yValItem, myItem.getImagePath(), DrawLayer.Entity,
////                            (screenManager -> {
////                                Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor Item");
////                                currentItem = myItem;
////                                // Adjust where text is rendered
////                                x_position = 765;
////                            })));
//                    k++;
////                }
//            }
//        }
    }
}
