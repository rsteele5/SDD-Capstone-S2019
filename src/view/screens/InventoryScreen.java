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
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


public class InventoryScreen extends GameScreen {
    /* Initialize variables *****************/
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    //renderablearray size
    private int rendArSize;
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
    private int invSQDist = 5;
    // X & Y Distance from image sides to first inv sq
    private int xDistToSQ = 10;
    private int yDistToSQ = 115;
    // Starting x value of inventory items.
    private int xValItemsStart = xValBG + xDistToSQ + invPadding;
    // x value spacing of inventory boxes.
    private int itemsSpacing = invSQSize + invSQDist;
    // Last inventory square's x value
    private int xValItemsEnd = xValItemsStart + (numSQHorz-1)* itemsSpacing;
    // Starting x value of inventory items.
    private int yValItemsStart = yValBG + yDistToSQ + invPadding;
    // Last inventory square's y value
    private int yValItemsEnd = yValItemsStart + (numSQVert-1)* itemsSpacing;
    // Number of squares right of first
    // Number of squares below first
    /* x and y positions for text */
    private int x_position;
    private int y_position = yValItemsStart;
    private int mainMenuX;
    private int mainMenuY = yValBG + (yDistToSQ/4);
    private int[] equipXPos = {(xValItemsEnd + ((5 * itemsSpacing)/2)),(xValItemsEnd + ((3 * itemsSpacing)/2)),
                                (xValItemsEnd + ((5 * itemsSpacing)/2)),(xValItemsEnd + (7 * (itemsSpacing)/2)),
                                (xValItemsEnd + ((5 * itemsSpacing)/2)),(xValItemsEnd + ((5 * itemsSpacing)/2))};
    private int[] equipYPos = {yValItemsStart, (yValItemsStart+ itemsSpacing),(yValItemsStart+ itemsSpacing),
                                (yValItemsStart+ itemsSpacing), (yValItemsStart+(2* itemsSpacing)),(yValItemsStart+(3* itemsSpacing))};
    private BufferedImage selectedIMG;
    private BufferedImage deselectedIMG;
    private BufferedImage buttonSpaceIMG;
    private ImageContainer itemContainer;


    private Button cBtn = null;
    private Item currentItem = null;
    /* ****************************************/

    /* TODO: Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
    private CopyOnWriteArrayList<Item> bearInventory;
    private Item[] bearEquipped = new Item[6];


    public InventoryScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "InventoryScreen";
        exclusivePopup = true;

        /* TODO Remove after testing. Populates inventories with items */
        bearInventory = new CopyOnWriteArrayList<>();
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());
        bearInventory.add(new Sword());bearInventory.add(new Sword());bearInventory.add(new Helmet());bearInventory.add(new Helmet());

        bearEquipped[0] = new Helmet();
        bearEquipped[3] = new Sword();
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
    /**
     * This layerInventoryButton method is specifically used to layer an item
     * on top of an inventory square and return it as one BufferedImage.
     * show the usage of various javadoc Tags.
     * @param img1 This parameter is the BufferedImage to be on bottom.
     * @param img2 This parameter is the BufferedImage to be on top.
     * @return BufferedImage This returns the layered image as a new BufferedImage
     */
    public static BufferedImage layerInventoryButton(BufferedImage img1, BufferedImage img2) {

        //Get the width/height of the larger image
        int wid = Math.max(img1.getWidth(),img2.getWidth());
        int height = Math.max(img1.getHeight(),img2.getHeight());
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //draw image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        //If the img2 is an item
        if(img2.getHeight() == 40 || img2.getHeight() == 100) g2.drawImage(img2, null, 2, 2);
        //If its the box and item for selection purposes
        else g2.drawImage(img2, null, 0, 0);
        g2.dispose();
        return newImage;
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
            selectedIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Inventory-Selected.png")));
            deselectedIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Inventory-Deselected.png")));
            buttonSpaceIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Inventory-Square.png")));
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
            BufferedImage teddyResizeIMG = resize( teddyImageIMG, 100, 150);
            BufferedImage slotResizeIMG = resize(buttonSpaceIMG, 100,100);

            /* Create main, item, and equipment buttons */
            mainMenuX = xValItemsStart + ((11*numSQHorz)/4)*(itemsSpacing)+ (selectedItemLabelIMG.getWidth());
            createButtons();
            createItemButtons();
            createEquipmentButtons();
            /* Create all renderables **/
            renderableLayers.get(0).add(new ImageContainer(xValBG, yValBG, backgroundIMG, 0));
            //TODO Find out why this mother fucker is off by 3 pixels
            renderableLayers.get(0).add(new ImageContainer((xValItemsEnd + ((7 * itemsSpacing)/2)) - (teddyResizeIMG.getWidth()/2) - (invSQSize/2) - invPadding- 3, yValItemsStart + ((9* itemsSpacing)/2) - invPadding, teddyResizeIMG, 0));
            renderableLayers.get(0).add(new ImageContainer( xValItemsStart,  yValBG + yDistToSQ/16, pauseMenuLabelIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(xValItemsStart + ((numSQHorz*(itemsSpacing))/2) - (itemsLabelIMG.getWidth()/2),  (yValBG + (5*yDistToSQ)/8), itemsLabelIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(xValItemsStart + ((3*numSQHorz)/2)*(itemsSpacing) - (equippedLabelIMG.getWidth()/2) ,  (yValBG + (5*yDistToSQ)/8), equippedLabelIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(xValItemsStart + ((11*numSQHorz)/4)*(itemsSpacing) - (selectedItemLabelIMG.getWidth()/2),  (yValBG + (5*yDistToSQ)/8), selectedItemLabelIMG, 0));
            x_position = xValItemsStart + ((11*numSQHorz)/4)*(itemsSpacing)- (selectedItemLabelIMG.getWidth()/2) +10;
            itemContainer = new ImageContainer(x_position+((3*selectedItemLabelIMG.getWidth())/16),  y_position + 7*35, slotResizeIMG, 0);
            renderableLayers.get(0).add(itemContainer);


            for (Button button : buttons)
                renderableLayers.get(button.getDrawLayer()).add(button);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loaded Success");
        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void updateTransitionOn() { currentState = ScreenState.Active; }

    @Override
    protected void updateTransitionOff() { exiting = true; }

    @Override
    protected void hiddenUpdate() { }

    @Override
    protected void activeUpdate() { }

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
        // Call this method to draw a string to the screen
        if (currentItem != null){
            graphics.setColor(Color.WHITE);
            Font font = new Font("NoScary", Font.BOLD, 36);
            graphics.setFont(font);
            graphics.drawString(currentItem.getItemName(), x_position, y_position+ 40);
            graphics.drawString("Type:     " + currentItem.getType() + "\n ", x_position, y_position + 2*35);
            graphics.drawString("Damage:    " + currentItem.getDamage()  + "\n ", x_position, y_position + 3*35);
            graphics.drawString("Immunity:   " + currentItem.getImmunity()  + "\n " , x_position, y_position + 4*35);
            graphics.drawString("CritChance: " + currentItem.getCritChance() + "%"  + "\n ", x_position, y_position + 5*35);
            graphics.drawString("Value:     $" + currentItem.getValue()  + "\n ", x_position, y_position + 6*35);
            //layerInventoryButton(resize(itemContainer.getCurrentImage(),100,100), resize(buttonSpaceIMG,100,100)));
            //int index = renderableLayers.get(0).size()-1;
            //renderableLayers.get(0).add(new ImageContainer(x_position,  y_position , currentItem.getCurrentImage(), 0));
            //renderableLayers.get(0).get(index-1).setCurrentImage(currentItem.getCurrentImage());//layerInventoryButton((resize(itemContainer.getCurrentImage(), 100, 100)), resize((currentItem.getCurrentImage()),100,100)));

            //x_position+((3*selectedItemLabelIMG.getWidth())/16),  y_position + 7*35
            graphics.setColor(Color.BLACK);
        }
    }

    private void createItemButtons(){
        try {
            int itemCount = bearInventory.size();
            int k = 0;
            /* Render item images (buttons) into bear's inventory */
            for (int y = yValItemsStart; y < yValItemsEnd+1; y+= itemsSpacing) {
                for (int x = xValItemsStart; x < xValItemsEnd+1; x+= itemsSpacing) {
                    if (k < numSQHorz*numSQVert) {
                        BufferedImage slot;
                        final Item myItem;
                        if(k  < itemCount) {
                            myItem = bearInventory.get(k);
                            slot = layerInventoryButton(buttonSpaceIMG, resize(RenderEngine.convertToARGB(
                                    ImageIO.read(getClass().getResource(myItem.getImagePath()))), 40, 40));
                        }else {
                            slot = buttonSpaceIMG;
                            myItem = null;
                        }
                        Button nBtn = new Button(x, y, slot, 1);
                        nBtn.setOnClick(screenManager -> {
                            Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Item");
                            if (cBtn != null)
                                cBtn.setCurrentImage(layerInventoryButton(cBtn.getCurrentImage(), deselectedIMG));
                            nBtn.setCurrentImage(layerInventoryButton(nBtn.getCurrentImage(), selectedIMG));
                            cBtn = nBtn;
                            currentItem = myItem;
                        });
                        buttons.add(nBtn);
                        k++;
                    }
                }
            }

        }catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }

    private void createEquipmentButtons(){
        try {
            BufferedImage slot;
            for(int k =0; k < bearEquipped.length; ++k) {
                final Item myItem;
                if (bearEquipped[k] != null) {
                    myItem = bearEquipped[k];
                    slot = layerInventoryButton(buttonSpaceIMG, resize(RenderEngine.convertToARGB(
                            ImageIO.read(getClass().getResource(myItem.getImagePath()))), 40, 40));
                } else {
                    slot = buttonSpaceIMG;
                    myItem = null;
                }
                Button nBtn = new Button(equipXPos[k], equipYPos[k], slot, 1);
                nBtn.setOnClick(screenManager -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Equipment");
                    if (cBtn != null)
                        cBtn.setCurrentImage(layerInventoryButton(cBtn.getCurrentImage(), deselectedIMG));
                    nBtn.setCurrentImage(layerInventoryButton(nBtn.getCurrentImage(), selectedIMG));
                    cBtn = nBtn;
                    currentItem = myItem;
                });
                buttons.add(nBtn);
            }
        }catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }

    private void createButtons(){
        try {

            BufferedImage menuButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-MainMenu.png")));
            BufferedImage saveButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Save.png")));
            BufferedImage optionsButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Options.png")));
            BufferedImage backButtonIMG = RenderEngine.convertToARGB(
                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Back.png")));

            /* Create buttons **/
            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY, menuButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Main Menu");
                        screenManager.addScreen(new MainMenuScreen(screenManager));
                    })));

            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY+saveButtonIMG.getHeight()+20, saveButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Save");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));

            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY+(2*saveButtonIMG.getHeight()+(2*20)), optionsButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Options");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));

            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY+(3*saveButtonIMG.getHeight()+(3*20)), backButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));

        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }
}
