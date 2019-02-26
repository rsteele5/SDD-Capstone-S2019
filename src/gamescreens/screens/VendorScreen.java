package gamescreens.screens;

import gameobjects.Clickable;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.TextBox;
import gameobjects.renderables.Vendor;
import gameobjects.renderables.buttons.ItemButton;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.items.Item;
import gameobjects.renderables.buttons.Button;
import gamescreens.containers.GridContainer;
import gamescreens.screens.menus.dev.DevScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {
    //region Variables ******************************/
    private ItemButton currentItemButton = null;
    private Item currentItem = null;


    /** Will load player's and vendor's items into arrays **/
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;
    //endregion ****************************************/

    public VendorScreen(ScreenManager screenManager) {
        super(screenManager, "VendorScreen", true, 150, 75);
    }

    @Override
    protected void initializeScreen() {
        Vendor myVendor = DevScreen.vendor;
        vendorInventory = myVendor.getItems();
        playerInventory = myVendor.getItems();

        for (RenderableObject renderable: myVendor.getRenderables()){
            renderable.addToScreen(this, false);
        }

        /* Create all renderables **/
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/VendorBackground.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(myVendor.getX(), myVendor.getY(), myVendor.getImagePath(), myVendor.getDrawLayer());
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(350, 335, "/assets/Teddy.png", DrawLayer.Entity);
        imageContainer.setSize(90, 140);
        imageContainer.addToScreen(this, true);


        //region Create screen buttons **/
        Button button;

        button = new Button(25, 25,
                "/assets/buttons/Button-Vendor-Exit.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                }));
        button.addToScreen(this, true);

        button = new Button(565, 485,
                "/assets/buttons/Button-Vendor-Buy.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        vendorInventory.remove(currentItem);
                        playerInventory.add(currentItem);
                        currentItem = null;
                    }
                }));
        button.addToScreen(this, true);

        button = new Button(330, 485,
                "/assets/buttons/Button-Vendor-Sell.png",
                DrawLayer.Entity,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    if (playerInventory.size() > 0 && currentItem != null) {
                        playerInventory.remove(currentItem);
                        vendorInventory.add(currentItem);
                        currentItem = null;
                    }
                }));
        button.addToScreen(this, true);
        //endregion

        // Create text boxes to hold item description
        /* x and y positions for text */
        int x_playerText = 275;
        int y_position = 125;
        TextBox itemDetailsPlayer = new TextBox(x_playerText, y_position, 210, 200, "",
                new Font("NoScary", Font.PLAIN, 26), Color.BLACK);
        itemDetailsPlayer.addToScreen(this,true);

        int x_vendorText = 550;
        TextBox itemDetailsVendor = new TextBox(x_vendorText, y_position, 210, 200, "",
                new Font("NoScary", Font.PLAIN, 26), Color.BLACK);

        itemDetailsVendor.addToScreen(this,true);

        // Create Gridlayout for player item buttons
        int rows = 7;
        int columns = 4;
        GridContainer playerItems = new GridContainer(this, rows, columns, 50, 125, 50, 150);
        ItemButton itemContainerButton;

        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                itemContainerButton = new ItemButton();
                playerItems.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(playerInventory.get(k));
                    ItemButton finalItemContainerButton = itemContainerButton;
                    itemContainerButton.setOnClick(GameScreen -> {
                        if (currentItemButton != null) {
                            currentItemButton.deSelect();
                            if (itemDetailsPlayer.getText().length() > 0){
                                itemDetailsPlayer.setText("");
                            }
                            if (itemDetailsVendor.getText().length() > 0){
                                itemDetailsVendor.setText("");
                            }
                        }
                        currentItemButton = finalItemContainerButton;
                        currentItem = currentItemButton.getItem();
                        itemDetailsPlayer.setText(currentItem.getDescription());
                    });
                    k++;
                }
            }
        }

        // Create Gridlayout for vendor item buttons
        GridContainer vendorItems = new GridContainer(this, rows, columns, 50, 125, 760, 150);

        count = vendorInventory.size();
        k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                itemContainerButton = new ItemButton();
                vendorItems.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(vendorInventory.get(k));
                    ItemButton finalItemContainerButton = itemContainerButton;
                    itemContainerButton.setOnClick(GameScreen -> {
                        if (currentItemButton != null) {
                            currentItemButton.deSelect();
                            // Reset previous item's text to ""
                            if (itemDetailsPlayer.getText().length() > 0){
                                itemDetailsPlayer.setText("");
                            }
                            if (itemDetailsVendor.getText().length() > 0){
                                itemDetailsVendor.setText("");
                            }
                        }
                        currentItemButton = finalItemContainerButton;
                        currentItem = currentItemButton.getItem();
                        itemDetailsVendor.setText(currentItem.getDescription());
                    });
                    k++;
                }
            }
        }

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
