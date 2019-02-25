package gamescreens.screens;

import gameobjects.Clickable;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.TextBox;
import gameobjects.renderables.buttons.ItemButton;
import gameobjects.renderables.items.Weapon;
import gameobjects.renderables.items.WeaponBuilder;
import gameobjects.renderables.items.WeaponType;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridLayoutContainer;
import main.utilities.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;


public class InventoryScreen extends GameScreen {
    /* Initialize variables *****************/
    //protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    //region<Variables>
    //renderablearray size
    private int rendArSize;
    // Squares horizontal
    private int numSQHorz = 4;
    // Squares vertical
    private int numSQVert = 8;
    // Starting x and y value of Background
    private final int xValBG = 180;
    private final int yValBG = 105;
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
    private int xValItemsEnd = xValItemsStart + (numSQHorz - 1) * itemsSpacing;
    // Starting x value of inventory items.
    private int yValItemsStart = yValBG + yDistToSQ + invPadding;
    // Last inventory square's y value
    private int yValItemsEnd = yValItemsStart + (numSQVert - 1) * itemsSpacing;
    // Number of squares right of first
    // Number of squares below first
    /* x and y positions for text */
    private int x_position;
    private int y_position = yValItemsStart;
    private int mainMenuX;
    private int mainMenuY = yValBG + (yDistToSQ / 4);
    private int[] equipXPos = {(xValItemsEnd + ((5 * itemsSpacing) / 2)), (xValItemsEnd + ((3 * itemsSpacing) / 2)),
            (xValItemsEnd + ((5 * itemsSpacing) / 2)), (xValItemsEnd + (7 * (itemsSpacing) / 2)),
            (xValItemsEnd + ((5 * itemsSpacing) / 2)), (xValItemsEnd + ((5 * itemsSpacing) / 2))};
    private int[] equipYPos = {yValItemsStart, (yValItemsStart + itemsSpacing), (yValItemsStart + itemsSpacing),
            (yValItemsStart + itemsSpacing), (yValItemsStart + (2 * itemsSpacing)), (yValItemsStart + (3 * itemsSpacing))};
    private BufferedImage selectedIMG;
    private BufferedImage deselectedIMG;
    private BufferedImage buttonSpaceIMG;

    private Button cBtn = null;
    private ItemButton currentItemButton = null;
    private TextBox itemDetails;
    //endregion
//
//    public static Inventory inv;
    /* ****************************************/

    /* TODO: Remove after testing. Create arrays for bear's and vendor's items (identified by image name here) **/
//    private CopyOnWriteArrayList<Item> bearInventory;
//    private Item[] bearEquipped = new Item[6];


    public InventoryScreen(ScreenManager screenManager) {
        super(screenManager, "InventoryScreen", true, 180, 105);

//        /* TODO Remove after testing. Populates inventories with items */
//        inv = new Inventory();
//        bearInventory = new CopyOnWriteArrayList<>();
//        for(Weapon wp : inv.getWeapons())
//        {bearInventory.add(wp); }
//        for(Armor ar : inv.getArmors())
//        {bearInventory.add(ar); }
//        for(Consumable co : inv.getConsumables())
//        {bearInventory.add(co);}
//
//        //bearInventory.add(inv.getWeapon(0));
//        //bearInventory.add(inv.getWeapon(1));
//
//
//        bearEquipped[0] = inv.getArmor(0);
//        bearEquipped[3] = inv.getWeapon(2);
    }

    /**
     * This resize method is used to resize a BufferedImage
     * to a new width and heigth.
     * show the usage of various javadoc Tags.
     * It will resize the image associated with the RenderableObject
     *
     * @param newW This parameter is new width of the image
     * @param newH This parameter is new height of the image
     * @return Resized Image
     * @void BufferedImage The resized image takes the place of the original
     */
//    public BufferedImage resize(BufferedImage img, int newW, int newH) {
////        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
////        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
////
////        Graphics2D g2d = dimg.createGraphics();
////        g2d.drawImage(tmp, 0, 0, null);
////        g2d.dispose();
////
////        return dimg;
////    }

    /**
     * This layerInventoryButton method is specifically used to layer an item
     * on top of an inventory square and return it as one BufferedImage.
     * show the usage of various javadoc Tags.
     *
     * @param img1 This parameter is the BufferedImage to be on bottom.
     * @param img2 This parameter is the BufferedImage to be on top.
     * @return BufferedImage This returns the layered image as a new BufferedImage
     */
//    public static BufferedImage layerInventoryButton(BufferedImage img1, BufferedImage img2) {
//
//        //Get the width/height of the larger image
//        int wid = Math.max(img1.getWidth(), img2.getWidth());
//        int height = Math.max(img1.getHeight(), img2.getHeight());
//        //create a new buffer and draw two image into the new image
//        BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2 = newImage.createGraphics();
//        Color oldColor = g2.getColor();
//        //draw image
//        g2.setColor(oldColor);
//        g2.drawImage(img1, null, 0, 0);
//        //If the img2 is an item
//        if (img2.getHeight() == 40 || img2.getHeight() == 96) g2.drawImage(img2, null, 2, 2);
//            //If its the box and item for selection purposes
//        else g2.drawImage(img2, null, 0, 0);
//        g2.dispose();
//        return newImage;
//    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {

        ImageContainer background;
        background = new ImageContainer(0,0,"/assets/backgrounds/BG-Inventory.png", DrawLayer.Background);
        background.addToScreen(this,true);

        ImageContainer teddy = new ImageContainer(30,30,"/assets/Teddy.png", DrawLayer.Entity);
        teddy.addToScreen(this,true);


        Weapon myWeap = new WeaponBuilder()
                .position(100, 100)
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword!")
                .type(WeaponType.Sword)
                .value(15)
                .buildWeapon();
        //addInactiveObject(myWeap);
        loadables.add(myWeap);

        myWeap.addToScreen(this, true);

        itemDetails = new TextBox(300,75, 300, 400, "" ,
                new Font("NoScary", Font.PLAIN, 60), Color.BLACK);

        itemDetails.addToScreen(this,true);

        ItemButton button = new ItemButton(200, 200, DrawLayer.Entity);
        button.setOnClick(GameScreen -> {
                    currentItemButton = button;
                    itemDetails.setText(button.getItem().getDescription());
                });

        button.addToScreen(this,true);

        button.setItem(myWeap);

        //TODO: overlay Gridlayout Test
        final int COUNT = 9;
        GridLayoutContainer items = new GridLayoutContainer(screenManager, this, 3, 3);
        for(int i = 0; i < COUNT; i++){
            items.add(new ItemButton());
        }


        //TODO: implement items.add(new ItemButton(), 1,2);

//        GameScreen -> {
//            currentItemButton = getItem();
//            itemDetails.setText(currentItemButton.getDescription());
//        });

        addOverlay(items);

    }

    public boolean handleClickEvent(int x, int y) {
        Debug.log(true, name + "- Handle click");
        for(Clickable thing: clickables) {
            if(thing.contains(x,y)) {
                thing.onClick(this);
                return true;
            }
        }
        if(currentItemButton != null){
            itemDetails.setText("");
            currentItemButton.deSelect();
            currentItemButton = null;
        }
        return false;
    }

    @Override
    protected void activeUpdate() {

    }

//    @Override
//    protected void loadContent() {
//        try {
//            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loading content");
//
//            /* Get images **/
//            BufferedImage backgroundIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/backgrounds/BG-Inventory.png")));
//            selectedIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Inventory-Selected.png")));
//            deselectedIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Inventory-Deselected.png")));
//            buttonSpaceIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Inventory-Square.png")));
//            // Get Labels
//            BufferedImage equippedLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
//                    .getResource("/assets/labels/Label-Equipped.png")));
//            BufferedImage itemsLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
//                    .getResource("/assets/labels/Label-Items.png")));
//            BufferedImage pauseMenuLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
//                    .getResource("/assets/labels/Label-Pause-Menu.png")));
//            BufferedImage selectedItemLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
//                    .getResource("/assets/labels/Label-Selected-Item.png")));
//            /* TODO: Change to reflect current user's bear */
//            /* TODO: Fix Bear Height */
//            BufferedImage teddyImageIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/Teddy.png")));
//            BufferedImage teddyResizeIMG = resize( teddyImageIMG, 100, 150);
//            BufferedImage slotResizeIMG = resize(buttonSpaceIMG, 100,100);
//
//            /* Create main, item, and equipment buttons */
//            mainMenuX = xValItemsStart + ((11*numSQHorz)/4)*(itemsSpacing)+ (selectedItemLabelIMG.getWidth());
//            createButtons();
//            createItemButtons();
//            createEquipmentButtons();
//            /* Create all renderables **/
//            renderableLayers.get(0).add(new ImageContainer(xValBG, yValBG, backgroundIMG, 0));
//            //TODO Find out why this mother fucker is off by 3 pixels
//            renderableLayers.get(0).add(new ImageContainer((xValItemsEnd + ((7 * itemsSpacing)/2)) - (teddyResizeIMG.getWidth()/2) - (invSQSize/2) - invPadding- 3, yValItemsStart + ((9* itemsSpacing)/2) - invPadding, teddyResizeIMG, 0));
//            renderableLayers.get(0).add(new ImageContainer( xValItemsStart,  yValBG + yDistToSQ/16, pauseMenuLabelIMG, 0));
//            renderableLayers.get(0).add(new ImageContainer(xValItemsStart + ((numSQHorz*(itemsSpacing))/2) - (itemsLabelIMG.getWidth()/2),  (yValBG + (5*yDistToSQ)/8), itemsLabelIMG, 0));
//            renderableLayers.get(0).add(new ImageContainer(xValItemsStart + ((3*numSQHorz)/2)*(itemsSpacing) - (equippedLabelIMG.getWidth()/2) ,  (yValBG + (5*yDistToSQ)/8), equippedLabelIMG, 0));
//            renderableLayers.get(0).add(new ImageContainer(xValItemsStart + ((11*numSQHorz)/4)*(itemsSpacing) - (selectedItemLabelIMG.getWidth()/2),  (yValBG + (5*yDistToSQ)/8), selectedItemLabelIMG, 0));
//            x_position = xValItemsStart + ((11*numSQHorz)/4)*(itemsSpacing)- (selectedItemLabelIMG.getWidth()/2) +10;
//
//            renderableLayers.get(0).add(new ImageContainer(x_position+((3*selectedItemLabelIMG.getWidth())/16),  y_position + 7*35, slotResizeIMG, 0));
//
//            for (Button button : buttons)
//                renderableLayers.get(button.getDrawLayer()).add(button);
//
//            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loaded Success");
//        } catch (IOException e) {
//            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
//        }
//    }

//    @Override
//    public void draw(Graphics2D graphics) {
//        for (CopyOnWriteArrayList<RenderableObject> layer : renderableLayers) {
//            for (RenderableObject gameObject : layer) {
//                gameObject.draw(graphics);
//            }
//        }
//        //Drawing the Item Info
//
//        if (currentItemButton != null){
//            currentItemButton.drawMe(graphics, x_position, y_position, false);
//        }
//        int index = renderableLayers.get(0).size() - 1;
//        //Drawing the Image
//
//        renderableLayers.get(0).get(index).setCurrentImage(layerInventoryButton(resize(buttonSpaceIMG, 100, 100), resize((currentItemButton ==null ? buttonSpaceIMG :currentItemButton.getCurrentImage()), 96, 96)));
//    }

    private void createItemButtons() {
//            int k = 0;
//            BufferedImage slot;
//            /* Render item images (buttons) into bear's inventory*/
//            for (int y = yValItemsStart; y < yValItemsEnd+1; y+= itemsSpacing) {
//                for (int x = xValItemsStart; x < xValItemsEnd+1; x+= itemsSpacing) {
//                    if (k < numSQHorz*numSQVert) {
//                        final Item myItem;
//                        if(k  < bearInventory.size()) {
//                            myItem = bearInventory.get(k);
//                            slot = layerInventoryButton(buttonSpaceIMG, resize(myItem.getCurrentImage(),40,40));
//                        }else {
//                            slot = buttonSpaceIMG;
//                            myItem = null;
//                        }
//                        Button nBtn = new Button(x, y, slot, 1);
//                        nBtn.setOnClick(screenManager -> {
//                            Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Item");
//                            if (cBtn != null)
//                                cBtn.setCurrentImage(layerInventoryButton(cBtn.getCurrentImage(), deselectedIMG));
//                            nBtn.setCurrentImage(layerInventoryButton(nBtn.getCurrentImage(), selectedIMG));
//                            cBtn = nBtn;
//                            currentItemButton = myItem;
//                        });
//                        buttons.add(nBtn);
//                        k++;
//                    }
//                }
//            }
    }

    private void createEquipmentButtons(){
//            BufferedImage slot;
//            for(int k =0; k < bearEquipped.length; ++k) {
//                final Item myItem;
//                if (bearEquipped[k] != null) {
//                    myItem = bearEquipped[k];
//                    slot = layerInventoryButton(buttonSpaceIMG, resize(myItem.getCurrentImage(),40,40));
//                } else {
//                    slot = buttonSpaceIMG;
//                    myItem = null;
//                }
//                Button nBtn = new Button(equipXPos[k], equipYPos[k], slot, 1);
//                nBtn.setOnClick(screenManager -> {
//                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Equipment");
//                    if (cBtn != null)
//                        cBtn.setCurrentImage(layerInventoryButton(cBtn.getCurrentImage(), deselectedIMG));
//                    nBtn.setCurrentImage(layerInventoryButton(nBtn.getCurrentImage(), selectedIMG));
//                    cBtn = nBtn;
//                    currentItemButton = myItem;
//                });
//                buttons.add(nBtn);
//            }
    }

    private void createButtons(){
//        try {
//
//            BufferedImage menuButtonIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-MainMenu.png")));
//            BufferedImage saveButtonIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Save.png")));
//            BufferedImage optionsButtonIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Options.png")));
//            BufferedImage backButtonIMG = RenderEngine.convertToARGB(
//                    ImageIO.read(getClass().getResource("/assets/buttons/Button-Back.png")));
//
//            /* Create buttons **/
//            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY, menuButtonIMG, 1,
//                    (screenManager1 -> {
//                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Main Menu");
//                        screenManager.addScreen(new MainMenuScreen(screenManager));
//                    })));
//
//            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY+saveButtonIMG.getHeight()+20, saveButtonIMG, 1,
//                    (screenManager1 -> {
//                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Save");
//                        this.setScreenState(ScreenState.TransitionOff);
//                    })));
//
//            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY+(2*saveButtonIMG.getHeight()+(2*20)), optionsButtonIMG, 1,
//                    (screenManager1 -> {
//                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Options");
//                        this.setScreenState(ScreenState.TransitionOff);
//                    })));
//
//            buttons.add(new Button(mainMenuX- ((3*menuButtonIMG.getWidth())/8), mainMenuY+(3*saveButtonIMG.getHeight()+(3*20)), backButtonIMG, 1,
//                    (screenManager1 -> {
//                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
//                        this.setScreenState(ScreenState.TransitionOff);
//                    })));
//
//        } catch (IOException e) {
//            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
//        }
    }
}
