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


public class InventoryScreen extends GameScreen {
    /* Initialize variables *****************/
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    // Squares horizontal
    private int numSQHorz = 4;
    // Squares vertical
    private int numSQVert = 8;
    // Starting x and y value of Background
    private int xValBG = 180;
    private int yValBG = 105;
    // Inventory square Padding
    private int invPadding = 2;
    // Inventory square size and distance between them x and y
    private int invSQSize = 44;
    private int invSQxDist = 5;
    // X & Y Distance from image sides to first inv sq
    private int xDistToSQ = 10;
    private int yDistToSQ = 115;
    // Starting x value of inventory items.
    private int xValItemsStart = xValBG + xDistToSQ + invPadding;
    // x value spacing of inventory boxes.
    private int xValItemsSpacing = invSQSize + invSQxDist;
    // Last inventory square's x value
    private int xValItemsEnd = xValItemsStart + (numSQHorz-1)*xValItemsSpacing;
    // Starting x value of inventory items.
    private int yValItemsStart = yValBG + yDistToSQ + invPadding;
    // y value spacing of inventory boxes.
    private int yValItemsSpacing = invSQSize + invSQxDist;
    // Last inventory square's y value
    private int yValItemsEnd = yValItemsStart + (numSQVert-1)*yValItemsSpacing;
    // Number of squares right of first
    // Number of squares below first
    /* x and y positions for text */
    private int x_position = 400;
    private int y_position = 220;

    private Item currentItem = null;
    private boolean updateInventory = false;
    /* ****************************************/

    /* TODO: Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<Item> bearInventory;


    public InventoryScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "InventoryScreen";
        exclusivePopup = true;

        /* TODO Remove after testing. Populates inventories with items **/
        bearInventory = new CopyOnWriteArrayList<>();
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());

    }
    /**
     * This resize method is used to resize a BufferedImage
     * to a new width and heigth.
     * show the usage of various javadoc Tags.
     * @param img This parameter is the BufferedImage to resize
     * @param newW  This parameter is new width of the image
     * @param newH  This parameter is new heigth of the image
     * @return BufferedImage This returns the resized image as a new BufferedImage
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
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
                    ImageIO.read(getClass().getResource("/assets/backgrounds/BG-Inventory.png")));
            // Get Labels
            BufferedImage equippedLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Equipped.png")));
            BufferedImage itemsLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Items.png")));
            BufferedImage pauseMenuLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Pause-Menu.png")));
            BufferedImage selectedItemLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Selected-Item.png")));
            /* TODO: Change to reflect current user's bear */
            /* TODO: Fix Bear Height */
            BufferedImage teddyImageIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/Teddy.png")));
            BufferedImage teddyResizeIMG = resize( teddyImageIMG, 100, 136);

            /* Create main buttons and item buttons */
            createButtons();
            createItemButtons();
            /* Create all renderables **/
            renderableLayers.get(0).add(new ImageContainer(xValBG, yValBG, backgroundIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(415, 413, teddyResizeIMG, 0));
            // TODO Change these positions to use the size of the labels and their middle to find where they should be
            renderableLayers.get(0).add(new ImageContainer(xValBG + xDistToSQ,  yValBG + yDistToSQ/16, pauseMenuLabelIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(xValBG + xDistToSQ + (3*(invSQSize/2)),  yValBG + 5*(yDistToSQ/8), itemsLabelIMG, 0));

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
            graphics.drawString("Type: " + currentItem.getType(), x_position, y_position + 20);
            graphics.drawString("Damage: " + currentItem.getDamage(), x_position, y_position + 2*20);
            graphics.drawString("Immunity: " + currentItem.getImmunity(), x_position, y_position + 3*20);
            graphics.drawString("CritChance: " + currentItem.getCritChance() + "%", x_position, y_position + 4*20);
            graphics.drawString("Value: $" + currentItem.getValue(), x_position, y_position + 5*20);
            graphics.drawString(currentItem.getDescription1(), x_position, y_position + 7*20);
            if (currentItem.getDescription2() != null) {
                graphics.drawString(currentItem.getDescription2(), x_position, y_position + 8*20);
            }
        }
    }

    private void createItemButtons(){
        try {
            int itemCount = bearInventory.size();
            int k = 0;
            /* Render item images (buttons) into bear's inventory **/
            for (int y = yValItemsStart; y < yValItemsEnd+1; y+=yValItemsSpacing) {
                for (int x = xValItemsStart; x < xValItemsEnd+1; x+=xValItemsSpacing) {
                    if (k < itemCount) {
                        Item myItem = bearInventory.get(k);
                        BufferedImage item = resize(RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(myItem.getImagePath()))),40, 40);
                        buttons.add(new Button(x, y, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Item");
                                    currentItem = myItem;
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

            /* Create buttons **/
            buttons.add(new Button(800, 400, exitButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));

        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }
}
