package gamescreens.screens;

import gameengine.GameEngine;
import gameobjects.Player;
import gameobjects.renderables.*;
import gameobjects.renderables.buttons.Button;
import gameobjects.renderables.buttons.ButtonText;
import gameobjects.renderables.buttons.ItemButton;
import gameobjects.renderables.items.*;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridContainer;
import gamescreens.screens.menus.MainMenuScreen;
import gamescreens.screens.menus.options.OptionScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class InventoryScreen extends GameScreen {
    /* Initialize variables *****************/
    //region<Variable Declarations>
    private Player.PlayerState previousPlayerState;
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> equipButtons;
    private ItemButton bigEquipment;
    private TextBox itemDetails;
    private ItemButton currentItemButton = null;
    private Item currentItem = null;
    private ButtonText useButton;
    Player player;

    //endregion

    public InventoryScreen(ScreenManager screenManager) {
        super(screenManager, "InventoryScreen", true, 140, 90);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {
        player = GameEngine.players.get(0);
        previousPlayerState = player.getState();
        player.setState(Player.PlayerState.asleep);
        playerInventory = player.getItems();
        playerButtons = new CopyOnWriteArrayList<>();
        equipButtons = new CopyOnWriteArrayList<>();

        //Add all the items in the dev screen player to the screen
        for (RenderableObject renderable: player.getRenderables()){
            renderable.addToScreen(this, false);
        }
        //Create Labels
        initLabels();
        //Create Menu Buttons
        initButtons();
        //Create Item Display Area
        initItemDisplay();
        //Create Item Buttons
        initItemButtons();
        //Create Equipment Buttons
        initEquipButtons();
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
                itemDetailsPlayer.setText(currentItem.getDescription(false));
                bigEquipment.setItem(currentItemButton.getItem());
                if(currentItem.getCategory().toString() == "Consumable"){
                    useButton.setText("Consume");
                } else if(currentItem.getCategory().toString() == "Armor" || currentItem.getCategory().toString() == "Weapon"){
                    useButton.setText("Equip");
                }
                Debug.success(true,currentItem.getCategory().toString());
            } else {
                currentItem = null;
                currentItemButton.deSelect();
                currentItemButton = null;
                useButton.setText("");
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

    private void initLabels() {

        //BackGround
        ImageContainer background;
        background = new ImageContainer(0,0,"/assets/backgrounds/BG-Inventory.png", DrawLayer.Background);
        background.setWidth(980);
        background.setHeight(540);
        background.addToScreen(this,true);

        //Fonts
        Font noScaryHeader1 = new Font("NoScary", Font.PLAIN, 96);
        Font noScaryHeader2 = new Font("NoScary", Font.PLAIN, 60);

        //Add menu Title
        TextBox menuTitle = new TextBox(5, 0, 400, 1, "Pause Menu",
                noScaryHeader1, Color.WHITE);
        menuTitle.addToScreen(this,true);

        //Item label
        TextBox items = new TextBox(75, 75, 250, 100, "Items",
                noScaryHeader2, Color.BLUE);
        items.addToScreen(this,true);

        //Equipped label
        TextBox equipped = new TextBox(260, 75, 250, 100, "Equipped",
                noScaryHeader2, Color.BLUE);
        equipped.addToScreen(this,true);

        //Selected item label
        TextBox selectedItem = new TextBox(450, 75, 250, 100, "Selected Item",
                noScaryHeader2, Color.BLUE);
        selectedItem.addToScreen(this,true);

        //Teddy
        ImageContainer teddy = new ImageContainer(295,370,"/assets/Teddy.png", DrawLayer.Entity);
        teddy.setWidth(70);
        teddy.setHeight(150);
        teddy.addToScreen(this,true);
    }

    private void initButtons() {
        //Main Menu Button
        Button mainMenuButton = new gameobjects.renderables.buttons.Button(765,30,
                "/assets/buttons/Button-MainMenu.png",
                DrawLayer.Entity,
                () ->{
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
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Save");
                });
        saveButton.setWidth(192);
        saveButton.setHeight(72);
        saveButton.addToScreen(this,true);

        //Options Button
        Button optionsButton = new gameobjects.renderables.buttons.Button(765,234,
                "/assets/buttons/Button-Options.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager));
                });
        optionsButton.setWidth(192);
        optionsButton.setHeight(72);
        optionsButton.addToScreen(this,true);

        //Back Button
        Button backButton = new gameobjects.renderables.buttons.Button(765,336,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    player.setState(previousPlayerState);
                    currentState = ScreenState.TransitionOff;
                });
        backButton.setWidth(192);
        backButton.setHeight(72);
        backButton.addToScreen(this,true);
    }

    private void initItemButtons() {
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
    }

    private void initEquipButtons(){
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
    }

    private void initItemDisplay() {
        //Item details text box
        itemDetails = new TextBox(460, 130, 300, 260, "",
                new Font("NoScary", Font.PLAIN, 40), Color.WHITE);
        itemDetails.addToScreen(this,true);

        //Setup the big equipment button
        bigEquipment =  new ItemButton(515,350, DrawLayer.Entity);
        bigEquipment.setWidth(100);
        bigEquipment.setHeight(100);
        //If you click the big view do nothing, can change this later
        bigEquipment.setOnClick( () -> bigEquipment.deSelect());
        bigEquipment.addToScreen(this, true);

        //Use Buttons
        Font noScaryHeader2 = new Font("NoScary", Font.PLAIN, 35);
        useButton = new ButtonText(503, 465, "/assets/buttons/Button-Inventory-Empty.png", DrawLayer.Entity,noScaryHeader2,Color.BLACK);
        useButton.addToScreen(this, true);
    }
}
