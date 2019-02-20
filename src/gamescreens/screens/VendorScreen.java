package view.screens;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.*;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {
    /* Initialize variables *****************/
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

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
    private boolean updateInventory = false;
    /* ****************************************/

    /** Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<Item> bearInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;


    public VendorScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "VendorScreen";
        exclusivePopup = true;

        /* Remove after testing. Populates inventories with red potion **/
        bearInventory = new CopyOnWriteArrayList<>();
        vendorInventory = new CopyOnWriteArrayList<>();
        bearInventory.add(new Sword());
        vendorInventory.add(new Potion());
        vendorInventory.add(new Potion());
        vendorInventory.add(new Sword());
        vendorInventory.add(new Helmet());
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

            /* TODO: Change to reflect current user's bear **/
            BufferedImage teddyImageIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/Teddy.png")));

            /* Create main buttons and item buttons */
            createButtons();
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

        // Called when buy or sell occurs
        if (updateInventory){
            // Delete previous button images and reset
            buttons.removeAll(buttons);

            // Delete and recreate layer
            renderableLayers.remove(1);
            renderableLayers.add(new CopyOnWriteArrayList<>());

            // Recreate buttons
            createButtons();
            createItemButtons();

            // Render buttons
            for (Button button : buttons)
                renderableLayers.get(button.getDrawLayer()).add(button);

            updateInventory = false;
        }

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
        try {
            /* Render item images (buttons) into bear's inventory **/
            int itemCount = bearInventory.size();
            int k = 0;
            for (int yValItem1 : yValItems) {
                for (int xValBearItem : xValBearItems) {
                    if (k < itemCount) {
                        Item myItem = bearInventory.get(k);
                        BufferedImage item = RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(myItem.getImagePath())));
                        buttons.add(new Button(xValBearItem, yValItem1, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Bear Item");
                                    currentItem = myItem;
                                    // Adjust where text is rendered
                                    x_position = 400;
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
                        Item myItem = vendorInventory.get(k);
                        BufferedImage item = RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(myItem.getImagePath())));
                        buttons.add(new Button(xValVendorItem, yValItem, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor Item");
                                    currentItem = myItem;
                                    // Adjust where text is rendered
                                    x_position = 765;
                                })));
                        k++;
                    }
                }
            }

        }catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }

    private void createButtons(){
        try {

            BufferedImage exitButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Vendor-Exit.png")));
            BufferedImage buyButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Vendor-Buy.png")));
            BufferedImage sellButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Vendor-Sell.png")));


        /* Create buttons **/
        buttons.add(new Button(175, 100, exitButtonIMG, 1,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                })));
        buttons.add(new Button(775, 560, buyButtonIMG, 1,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        vendorInventory.remove(currentItem);
                        bearInventory.add(currentItem);
                        currentItem = null;
                        updateInventory = true;
                    }
                })));
        buttons.add(new Button(400, 560, sellButtonIMG, 1,
                (screenManager1 -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    if (bearInventory.size() > 0 && currentItem != null) {
                        bearInventory.remove(currentItem);
                        vendorInventory.add(currentItem);
                        currentItem = null;
                        updateInventory = true;
                    }
                })));

        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }
}
