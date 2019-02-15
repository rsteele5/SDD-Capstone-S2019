package view.screens;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
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


            /* Create buttons **/
            buttons.add(new Button(175, 100, exitButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Exit Vendor");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));
            buttons.add(new Button(590, 225, buyButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Buy from Vendor");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));
            buttons.add(new Button(590, 300, sellButtonIMG, 1,
                    (screenManager1 -> {
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Sell to Vendor");
                        this.setScreenState(ScreenState.TransitionOff);
                    })));


            /* Render item images into bear's inventory **/
            int itemCount = bearInventory.size();
            int k = 0;
            for (int yValItem1 : yValItems) {
                for (int xValBearItem : xValBearItems) {
                    if (k < itemCount) {
                        BufferedImage item = RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(bearInventory.get(k))));
                        buttons.add(new Button(xValBearItem, yValItem1, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Bear Item");
                                    // TODO: get item description & value to display on small overlay screen
                                })));
                        k++;
                    }
                }
            }
            /* Render item images into vendor's inventory **/
            itemCount = vendorInventory.size();
            k = 0;
            for (int yValItem : yValItems) {
                for (int xValVendorItem : xValVendorItems) {
                    if (k < itemCount) {
                        BufferedImage item = RenderEngine.convertToARGB(
                                ImageIO.read(getClass().getResource(vendorInventory.get(k))));
                        buttons.add(new Button(xValVendorItem, yValItem, item, 1,
                                (screenManager -> {
                                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Vendor Item");
                                    // TODO: get item description & cost to display on small overlay screen
                                })));
                        renderableLayers.get(1).add(new ImageContainer(xValVendorItem, yValItem, item, 1));
                        k++;
                    }
                }
            }

            /* Create all renderables **/
            renderableLayers.get(0).add(new ImageContainer(150, 75, backgroundIMG, 0));
            renderableLayers.get(0).add(new ImageContainer(750, 450, vendorImageIMG, 0));

            for (Button button: buttons)
                renderableLayers.get(button.getDrawLayer()).add(button);


            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loaded Success");
        }catch(IOException e) {
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
}
