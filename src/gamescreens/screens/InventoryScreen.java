package gamescreens.screens;

import gameobjects.Clickable;
import gameobjects.Player;
import gameobjects.renderables.*;
import gameobjects.renderables.buttons.Button;
import gameobjects.renderables.buttons.ItemButton;
import gameobjects.renderables.items.*;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridContainer;
import gamescreens.screens.menus.MainMenuScreen;
import gamescreens.screens.menus.DevScreen;
import gamescreens.screens.menus.options.OptionScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class InventoryScreen extends GameScreen {
    /* Initialize variables *****************/
    //region<Variable Declarations>
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> equipButtons;
    ItemButton bigEquipment;
    private TextBox itemDetails;
    private ItemButton currentItemButton = null;
    private Item currentItem = null;

    //endregion

    public InventoryScreen(ScreenManager screenManager) {
        super(screenManager, "InventoryScreen", true, 140, 90);

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
        Player player = DevScreen.player;
        playerInventory = player.getItems();
        playerButtons = new CopyOnWriteArrayList<>();
        equipButtons = new CopyOnWriteArrayList<>();

        //Add all the items in the dev screen player to the screen
        for (RenderableObject renderable: DevScreen.player.getRenderables()){
            renderable.addToScreen(this, false);
        }

        ImageContainer background;
        background = new ImageContainer(0,0,"/assets/backgrounds/BG-Inventory.png", DrawLayer.Background);
        background.setWidth(980);
        background.setHeight(540);
        background.addToScreen(this,true);

        //Add menu Title
        TextBox menuTitle = new TextBox(5, 0, 400, 1, "Pause Menu",
                new Font("NoScary", Font.PLAIN, 96), Color.WHITE);
        menuTitle.addToScreen(this,true);

        //Item label
        TextBox items = new TextBox(75, 75, 250, 100, "Items",
                new Font("NoScary", Font.PLAIN, 60), Color.BLUE);
        items.addToScreen(this,true);

        //Equipped label
        TextBox equipped = new TextBox(260, 75, 250, 100, "Equipped",
                new Font("NoScary", Font.PLAIN, 60), Color.BLUE);
        equipped.addToScreen(this,true);

        //Selected item label
        TextBox selectedItem = new TextBox(450, 75, 250, 100, "Selected Item",
                new Font("NoScary", Font.PLAIN, 60), Color.BLUE);
        selectedItem.addToScreen(this,true);

        //Main Menu Button
        Button mainMenuButton = new gameobjects.renderables.buttons.Button(765,30,
                "/assets/buttons/Button-MainMenu.png",
                DrawLayer.Entity,
                () ->{
                    //TODO make this go back to the main menu
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    screenManager.addScreen(new MainMenuScreen(screenManager));
                });
        mainMenuButton.setWidth(192);
        mainMenuButton.setHeight(72);
        mainMenuButton.addToScreen(this,true);

        //Save Button
        Button saveButton = new gameobjects.renderables.buttons.Button(765,132,
                "/assets/buttons/Button-Save.png",
                DrawLayer.Entity,
                () ->{
                    //TODO Save stuff
                });
        saveButton.setWidth(192);
        saveButton.setHeight(72);
        saveButton.addToScreen(this,true);

        //Options Button
        Button optionsButton = new gameobjects.renderables.buttons.Button(765,234,
                "/assets/buttons/Button-Options.png",
                DrawLayer.Entity,
                () ->{
                    screenManager.addScreen(new OptionScreen(screenManager));
                });
        optionsButton.setWidth(192);
        optionsButton.setHeight(72);
        optionsButton.addToScreen(this,true);

        //Back Button
        Button backButton = new gameobjects.renderables.buttons.Button(765,336,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> currentState = ScreenState.TransitionOff
        );
        backButton.setWidth(192);
        backButton.setHeight(72);
        backButton.addToScreen(this,true);

        //Item details text box
        itemDetails = new TextBox(460, 130, 210, 260, "",
                new Font("NoScary", Font.PLAIN, 30), Color.BLACK);
        itemDetails.addToScreen(this,true);

        //Set up the grid for the player inventory
        int rows = 7;
        int columns = 4;
        GridContainer playerGrid = new GridContainer(this, rows, columns, 50, 125, 15, 140);

        //region Add buttons to the Grid Containers
        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                playerGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(playerInventory.get(k));
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetails);
                // Add button to array so it can be accessed again later
                playerButtons.add(itemContainerButton);
            }
        }

        //Create grid for the cross pattern equipment stuff
        GridContainer equipGrid = new GridContainer(this, 4, 3, 50, 125, 250, 140);

        //Equipment Buttons
        ItemButton equipHead =  new ItemButton();
        equipGrid.addAt(equipHead, 0, 1);
        equipButtons.add(equipHead);
        setClickEvent(equipHead, itemDetails);

        ItemButton equipOffHand =  new ItemButton();
        equipGrid.addAt(equipOffHand, 1, 0);
        equipButtons.add(equipOffHand);
        setClickEvent(equipOffHand, itemDetails);

        ItemButton equipChest =  new ItemButton();
        equipGrid.addAt(equipChest, 1, 1);
        equipButtons.add(equipChest);
        setClickEvent(equipChest, itemDetails);

        ItemButton equipWeapon =  new ItemButton();
        equipGrid.addAt(equipWeapon, 1, 2);
        equipButtons.add(equipWeapon);
        setClickEvent(equipWeapon, itemDetails);

        ItemButton equipLegs =  new ItemButton();
        equipGrid.addAt(equipLegs, 2, 1);
        equipButtons.add(equipLegs);
        setClickEvent(equipLegs, itemDetails);

        ItemButton equipFeet =  new ItemButton();
        equipGrid.addAt(equipFeet, 3, 1);
        equipButtons.add(equipFeet);
        setClickEvent(equipFeet, itemDetails);

        ImageContainer teddy = new ImageContainer(295,370,"/assets/Teddy.png", DrawLayer.Entity);
        teddy.setWidth(70);
        teddy.setHeight(150);
        teddy.addToScreen(this,true);

        //Setup the big equipment button
        bigEquipment =  new ItemButton(515,420, DrawLayer.Entity);
        bigEquipment.setWidth(100);
        bigEquipment.setHeight(100);
        //If you click the big view do nothing, can change this later
        bigEquipment.setOnClick( () -> bigEquipment.deSelect());
        bigEquipment.addToScreen(this, true);



//        ImageContainer teddy = new ImageContainer(30,30,"/assets/Teddy.png", DrawLayer.Entity);
//        teddy.addToScreen(this,true);
//
//        Armor myArmor = new ArmorBuilder()
//                .position(200,200)
//                .imagePath("/src/assets/Items/helmet1.png")
//                .name("My Fwirst Helmet")
//                .type(Head)
//                .value(20)
//                .buildWeapon();
//        loadables.add(myArmor);
//        //myArmor.addToScreen(this, true);
//
//        // Weapon button test
//        Weapon myWeap = new WeaponBuilder()
//                .position(100, 100)
//                .imagePath("/assets/Items/sword1.png")
//                .name("My Fwirst Sword!")
//                .type(WeaponType.Sword)
//                .value(15)
//                .buildWeapon();
//        //addInactiveObject(myWeap);
//        loadables.add(myWeap);
//
//        myWeap.addToScreen(this, true);
//
//        itemDetails = new TextBox(300,75, 300, 400, "" ,
//                new Font("NoScary", Font.PLAIN, 40), Color.BLACK);
//
//        itemDetails.addToScreen(this,true);
//
//        ItemButton button = new ItemButton(200, 200, DrawLayer.Entity);
//        button.setOnClick(() -> {
//                    currentItemButton = button;
//                    itemDetails.setText(button.getItem().getDescription());
//                });
//
//        button.addToScreen(this,true);
//        button.setItem(myWeap);
//
//
//        // Consumable button test
//        Consumable myCons = new ConsumableBuilder()
//                .position(800, 100)
//                .imagePath("/assets/Items/bluepotion.png")
//                .name("My Fwirst Potion!")
//                .type(ConsumableType.edible)
//                .value(15)
//                .buildConsumable();
//        //addInactiveObject(myWeap);
//        loadables.add(myCons);
//
//        //myCons.addToScreen(this, true);
//
//        ItemButton buttonCons = new ItemButton(200, 250, DrawLayer.Entity);
//        buttonCons.setOnClick(() -> {
//            currentItemButton = buttonCons;
//            itemDetails.setText(buttonCons.getItem().getDescription());
//        });
//
//        buttonCons.addToScreen(this,true);
//        buttonCons.setItem(myCons);
//
//
//
//        //TODO: overlay Gridlayout Test
//        GridContainer items = new GridContainer(this, 3, 3, 50, 50, 10, 250);
//        ItemButton itemContainerButton;
//        for(int i = 0; i < 3; i++){
//            for(int j = 0; j < 3; j++){
//            itemContainerButton = new ItemButton();
//            items.addAt(itemContainerButton, i, j);
//        }
//
//        GridContainer equipped = new GridContainer(this, 4, 3, 50, 50, 260, 250);
//        ItemButton equippedContainerButton;
//
//        equippedContainerButton = new ItemButton();
//        equipped.addAt(equippedContainerButton, 0, 1);
//        equippedContainerButton = new ItemButton();
//        equipped.addAt(equippedContainerButton, 1, 0);
//        equippedContainerButton = new ItemButton();
//        equipped.addAt(equippedContainerButton, 1, 1);
//        equippedContainerButton = new ItemButton();
//        equipped.addAt(equippedContainerButton, 1, 2);
//        equippedContainerButton = new ItemButton();
//        equipped.addAt(equippedContainerButton, 2, 1);
//        equippedContainerButton = new ItemButton();
//        equipped.addAt(equippedContainerButton, 3, 1);
//
//        }
//
//
//        //TODO: implement items.add(new ItemButton(), 1,2);
//

    }

    private void setClickEvent(ItemButton itemContainerButton, TextBox itemDetailsPlayer){
        itemContainerButton.setOnClick(() -> {
            if (currentItemButton != null) {
                currentItemButton.deSelect();
                // Reset previous item's text to ""
                if (itemDetailsPlayer.getText().length() > 0) {
                    itemDetailsPlayer.setText("");
                    bigEquipment.setItem(null);
                }
            }
            currentItemButton = itemContainerButton;
            currentItem = currentItemButton.getItem();

            if (currentItemButton.getItem() != null) {
                itemDetailsPlayer.setText(currentItem.getDescription());
                bigEquipment.setItem(currentItemButton.getItem());
            } else {
                currentItem = null;
                currentItemButton.deSelect();
                currentItemButton = null;
            }
        });
    }

    private void resetButtonItems(){

        // Reset all player item buttons to null, set item buttons again, and establish click events
        int count = playerInventory.size();
        int k = 0;
        for (ItemButton pbutton : playerButtons) {
            pbutton.resetItem();
            if (k < count){
                pbutton.setItem(playerInventory.get(k));
                setClickEvent(pbutton, itemDetails);
                k++;
            }
        }
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
