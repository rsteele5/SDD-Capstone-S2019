package view.screens;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.RenderableObject;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;


public class VendorScreen extends GameScreen {
    /* Initialize variables *****************/
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    /* Array of x values for bear item box locations **/
    private int [] xValBearItems = {191, 239, 287, 335};

    /* Array of x values for vendor item box locations **/
    private int [] xValVendorItems = {927, 975, 1023, 1071};

    /* Array of y values for bear AND vendor item box locations **/
    private int [] yValItems = {220, 266, 313, 360, 407, 455, 502, 549};

    /* TEMPORARY text for displaying the item information. //TODO: update to item information when available **/
    private String itemName = "";
    private String type = "Potion";
    private String damage = "0";
    private String immunity = "2-4";
    private int critChance = 6;
    private String description1 = "This will boost your fire";
    private String description2 = "resistance!";
    private int value = 5;

    /* x and y positions for text */
    private int x_position = 765;
    private int y_position = 220;
    private String fullDescription = "";

    /* Off-screen rendering */
    private Graphics2D graphics;
    /* ****************************************/

    /** Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<String> bearInventory;
    private CopyOnWriteArrayList<String> vendorInventory;
    private String itemsPath = "/assets/Items/";

    public VendorScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "VendorScreen";
        exclusivePopup = true;

        /* Remove after testing. Populates inventories with red potion **/
        bearInventory = new CopyOnWriteArrayList<>();
        vendorInventory = new CopyOnWriteArrayList<>();

        String path = "/assets/Items/redPotionSmall.png";
        for (int i = 0; i < 12; i++){
            bearInventory.add(path);
        }
        for (int i = 0; i < 21; i++){
            vendorInventory.add(path);
        }
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    protected void loadContent() {
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loading content");

            /* Get images **/
            BufferedImage backgroundIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/VendorBackground.png")));
            BufferedImage vendorImageIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/Vendor.png")));
                        BufferedImage exitButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Vendor-Exit.png")));
            BufferedImage buyButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Vendor-Buy.png")));
            BufferedImage sellButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Vendor-Sell.png")));

            /* TODO: Change to reflect current user's bear **/
            BufferedImage teddyImageIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/Teddy.png")));

            /* Create buttons **/
            buttons.add(new Button(175, 100, exitButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));
            buttons.add(new Button(775, 560, buyButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                        // TODO: Add buy function
                    })));
            buttons.add(new Button(400, 560, sellButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                        // TODO: Add sell function
                    })));
            createItemButtons();

            /* Create all renderables **/
            renderableLayers.get(0).add(new ImageContainer(150, 75, backgroundIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(765, 410, vendorImageIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(400, 380, teddyImageIMG, 0));

            for (Button button : buttons)
                renderableLayers.get(button.getDrawLayer()).add(button);


            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loaded Success");
        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void updateTransitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void updateTransitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {
        //createItemButtons();
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
        for (CopyOnWriteArrayList<RenderableObject> layer : renderableLayers) {
            for (RenderableObject gameObject : layer) {
                gameObject.draw(graphics);
            }
        }

        //Call this method to draw a string to the screen///////////////
        if (itemName != ""){
            graphics.setColor(Color.BLACK);
            graphics.drawString(itemName, x_position, y_position);
            graphics.drawString("Type: " + type, x_position, y_position += 20);
            graphics.drawString("Damage: " + damage, x_position, y_position += 20);
            graphics.drawString("Immunity: " + immunity, x_position, y_position += 20);
            graphics.drawString("CritChance: " + critChance + "%", x_position, y_position += 20);
            graphics.drawString("Cost: $" + value, x_position, y_position += 20);
            graphics.drawString(description1, x_position, y_position += 30);
            graphics.drawString(description2, x_position, y_position += 20);
        }


        // reset y_position
        y_position = 220;
    }

    private void createItemButtons(){
        try {
            /* Render item images (buttons) into bear's inventory **/
            int itemCount = bearInventory.size();
            int k = 0;
            for (int yValItem1 : yValItems) {
                for (int xValBearItem : xValBearItems) {
                    if (k < itemCount) {
                        BufferedImage item = RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(bearInventory.get(k))));
                        buttons.add(new Button(xValBearItem, yValItem1, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
                                    // TODO: get item description & value to display on small overlay screen
                                })));
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
                        BufferedImage item = RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(vendorInventory.get(k))));
                        buttons.add(new Button(xValVendorItem, yValItem, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor Item");
                                    x_position = 765;
                                    itemName = "Fire Potion";
                                })));
                        k++;
                    }
                }
            }

        }catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }
}
