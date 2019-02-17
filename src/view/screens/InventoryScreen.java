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

    // Starting x value of inventory items.
    private int xValItemsStart = 191;
    // x value spacing of inventory boxes.
    private int xValItemsSpacing = 48;
    // Last inventory square's x value
    private int xValItemsEnd = xValItemsStart + 3*xValItemsSpacing;
    // Starting x value of inventory items.
    private int yValItemsStart = 220;
    // y value spacing of inventory boxes.
    private int yValItemsSpacing = 46;
    // Last inventory square's y value
    private int yValItemsEnd = yValItemsStart + 7*yValItemsSpacing;

    /* Array of x values for teddy item box locations
    private int [] xValBearItems = {191, 239, 287, 335};

    /* Array of x values for vendor item box locations *
    private int [] xValVendorItems = {927, 975, 1023, 1071};

    /* Array of y values for bear AND vendor item box locations
    private int [] yValItems = {220, 266, 313, 360, 407, 455, 502, 549};*/

    /* x and y positions for text */
    private int x_position = 400;
    private int y_position = 220;

    private Item currentItem = null;
    private boolean updateInventory = false;
    /* ****************************************/

    /* Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<Item> bearInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;


    public InventoryScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "InventoryScreen";
        exclusivePopup = true;

        /* Remove after testing. Populates inventories with items **/
        bearInventory = new CopyOnWriteArrayList<>();
        bearInventory.add(new Sword());
        bearInventory.add(new Potion());
        bearInventory.add(new Potion());
        bearInventory.add(new Sword());
        bearInventory.add(new Helmet());

    }

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

            /* TODO: Change to reflect current user's bear **/
            BufferedImage teddyImageIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/Teddy.png")));
            BufferedImage teddyResizeIMG = resize( teddyImageIMG, 100, 136);

            /* Create main buttons and item buttons */
            createButtons();
            createItemButtons();

            /* Create all renderables **/
            renderableLayers.get(0).add(new ImageContainer(150, 75, backgroundIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(415, 413, teddyResizeIMG, 0));

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
            buttons.add(new Button(175, 100, exitButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));

        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }
}
