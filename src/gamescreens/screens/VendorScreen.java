package gamescreens.screens;

import gameengine.GameEngine;
import gameobjects.Player;
import gameobjects.renderables.*;
import gameobjects.renderables.buttons.ItemButton;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.items.Item;
import gameobjects.renderables.buttons.Button;
import gamescreens.containers.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {
    //region Variables ******************************/
    private ItemButton currentItemButton = null;
    private Item currentItem = null;
    private TextBox itemDetailsVendor;
    private TextBox itemDetailsPlayer;
    private TextBox goldTextBox;
    private String goldText;
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> vendorButtons;
    private Player player;
    private Vendor vendor;
    //endregion ****************************************/

    public VendorScreen(ScreenManager screenManager) {
        super(screenManager, "VendorScreen", true, 150, 75);
    }

    @Override
    protected void initializeScreen() {
        int x_vendor = 570;
        int y_vendor = 335;
        int x_player = 330;


        //region Initialize variables
        vendor = GameEngine.vendor;
        player = GameEngine.players.get(0);
        vendorInventory = vendor.getItems();
        playerInventory = player.getItems();
        playerButtons = new CopyOnWriteArrayList<>();
        vendorButtons = new CopyOnWriteArrayList<>();
        //endregion

        //region Create all renderables
        for (RenderableObject renderable: vendor.getRenderables()){
            renderable.addToScreen(this, false);
        }
        for (RenderableObject renderable: player.getRenderables()){
            renderable.addToScreen(this, false);
        }

        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/VendorBackground.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(x_vendor, y_vendor, "/assets/Vendor.png", vendor.getDrawLayer());
        imageContainer.setSize(150,150);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(x_player, y_vendor, "/assets/Teddy.png", player.getDrawLayer());
        imageContainer.setSize(90, 140);
        imageContainer.addToScreen(this, true);
        //endregion

        //region Create screen buttons **/
        Button button;

        button = new Button(25, 25,
                "/assets/buttons/Button-Vendor-Exit.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);

        button = new Button(585, 485,
                "/assets/buttons/Button-Vendor-Buy.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        // Item must be in vendor's inventory
                        if (vendorInventory.indexOf(currentItem) >= 0) {
                            // Verify that player has enough gold
                            if (player.getGold() >= currentItem.getValue()) {
                                // Confirmation
                                screenManager.addScreen(new ConfirmationPopup(screenManager,
                                        "You want to purchase " + currentItem.getItemName() +
                                                " for " + currentItem.getValue() + " gold pieces. Is this correct?",
                                        () -> {
                                            // Move item between inventory arrays
                                            vendorInventory.remove(currentItem);
                                            currentItem.depreciate();
                                            playerInventory.add(currentItem);
                                            playerInventory.sort(new SortByType());
                                            // Decrease player's gold and display on screen
                                            player.changeGold(-currentItem.getValue());
                                            goldTextBox.setText("");
                                            goldTextBox.setText(getGoldText());
                                            // Reset button items to the updated inventory arrays
                                            resetButtonItems();
                                            currentItemButton.deSelect();
                                            currentItem = null;
                                            itemDetailsVendor.setText("");
                                        }));
                            }
                            else {
                                screenManager.addScreen(new ConfirmationPopup(screenManager,
                                        "You don't have enough gold for this purchase. Continue shopping?",
                                        ()->{},
                                        ()-> this.exiting = true));
                            }
                        }
                    }
                });
        button.addToScreen(this, true);

        button = new Button(310, 485,
                "/assets/buttons/Button-Vendor-Sell.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    if (playerInventory.size() > 0 && currentItem != null) {
                        // Item must be in player's inventory
                        if (playerInventory.indexOf(currentItem) >= 0){
                            // Confirmation
                            screenManager.addScreen(new ConfirmationPopup(screenManager,
                                    "I will buy your " + currentItem.getItemName() +
                                            " for " + currentItem.getValue() + " gold pieces. Is this correct?",
                                    ()-> {
                                        // Move item between inventory arrays
                                        playerInventory.remove(currentItem);
                                        vendorInventory.add(currentItem);
                                        vendorInventory.sort(new SortByType());
                                        // Increase player's gold and display on screen
                                        player.changeGold(currentItem.getValue());
                                        goldTextBox.setText("");
                                        goldTextBox.setText(getGoldText());
                                        // Reset button items to the updated inventory arrays
                                        resetButtonItems();
                                        currentItemButton.deSelect();
                                        currentItem = null;
                                        itemDetailsPlayer.setText("");
                                    }));
                        }
                    }
                });
        button.addToScreen(this, true);

        // Test button
        button = new Button(460, 480,
                "/assets/buttons/Button-Test.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Test Vendor");
                    screenManager.addScreen(new VendorDialogBoxScreen(screenManager));
                });
        button.setSize(100, 50);
        button.addToScreen(this, true);
        //endregion

        //region Create text boxes to hold item description
        /* x and y positions for text */
        int x_playerText = 270;
        int y_position = 125;
        int x_vendorText = 550;
        itemDetailsPlayer = new TextBox(x_playerText, y_position, 210, 200, "",
                new Font("NoScary", Font.PLAIN, 24), Color.BLACK);
        itemDetailsPlayer.addToScreen(this,true);
        itemDetailsVendor = new TextBox(x_vendorText, y_position, 210, 200, "",
                new Font("NoScary", Font.PLAIN, 24), Color.BLACK);
        itemDetailsVendor.addToScreen(this,true);
        //endregion

        //region TextBox to hold player's available gold
        int x_goldText = 850;
        int y_goldText = 20;
        goldText = getGoldText();
        goldTextBox = new TextBox(x_goldText, y_goldText, 150, 50, goldText,
                new Font("NoScary", Font.PLAIN, 48), Color.BLACK);
        goldTextBox.addToScreen(this, true);
        //endregion

        // Create GridContainers for player and vendor item buttons
        int rows = 7;
        int columns = 4;
        GridContainer playerGrid = new GridContainer(this, rows, columns, 50, 125, 50, 150);
        GridContainer vendorGrid = new GridContainer(this, rows, columns, 50, 125, 760, 150);

        //region Add buttons to the Grid Containers
        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                playerGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(playerInventory.get(k));
                    //setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "player" );
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "player" );
                // Add button to array so it can be accessed again later
                playerButtons.add(itemContainerButton);
            }
        }

        count = vendorInventory.size();
        k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                vendorGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(vendorInventory.get(k));
                    //setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "vendor");
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "vendor");
                // Add assigned button to array so it can be accessed again later
                vendorButtons.add(itemContainerButton);
            }
        }
        //endregion
    }


    private void setClickEvent(ItemButton itemContainerButton, TextBox itemDetailsPlayer, TextBox itemDetailsVendor, String sender){
        itemContainerButton.setOnClick(() -> {
            if (currentItemButton != null) {
                currentItemButton.deSelect();
                // Reset previous item's text to ""
                if (itemDetailsPlayer.getText().length() > 0) {
                    itemDetailsPlayer.setText("");
                }
                if (itemDetailsVendor.getText().length() > 0) {
                    itemDetailsVendor.setText("");
                }
            }
            currentItemButton = itemContainerButton;
            currentItem = currentItemButton.getItem();
            if (currentItemButton.getItem() != null) {
                if (sender.equals("vendor")) {
                    itemDetailsVendor.setText(currentItem.getDescription());
                } else itemDetailsPlayer.setText(currentItem.getDescription());
            } else {
                currentItem = null;
                currentItemButton.deSelect();
                currentItemButton = null;
            }
        });
    }

    private void resetButtonItems(){
        // Reset all vendor item buttons to null, set item buttons again, and establish click events
        int count = vendorInventory.size();
        int k = 0;
        for (ItemButton vbutton : vendorButtons) {
            vbutton.resetItem();
            if (k < count){
                vbutton.setItem(vendorInventory.get(k));
                setClickEvent(vbutton, itemDetailsPlayer, itemDetailsVendor, "vendor" );
                k++;
            }
        }

        // Reset all player item buttons to null, set item buttons again, and establish click events
        count = playerInventory.size();
        k = 0;
        for (ItemButton pbutton : playerButtons) {
            pbutton.resetItem();
            if (k < count){
                pbutton.setItem(playerInventory.get(k));
                setClickEvent(pbutton, itemDetailsPlayer, itemDetailsVendor, "player" );
                k++;
            }
        }
    }

    public String getGoldText(){
        return "Gold: " + player.getGold();
    }

    @Override
    protected void transitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void transitionOff() {
        // Change player's image back to overworld image
        player.setImage("/assets/overworld/Overworld-Teddy.png");
        // Update the original inventory arrays with all the changes that have been made here.
        if (vendorInventory != null && playerInventory != null) {
            vendor.replaceList(vendorInventory);
            player.replaceList(playerInventory);
        } else {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "VendorScreen: Unable to overwrite inventory list");
        }
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }

}
