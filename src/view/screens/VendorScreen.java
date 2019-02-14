package view.screens;

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
            BufferedImage background = ImageIO.read(getClass().getResource("/assets/VendorBackground.png"));
            BufferedImage vendorImage = ImageIO.read(getClass().getResource("/assets/Vendor.png"));
            BufferedImage exitButton = ImageIO.read(getClass().getResource("/assets/ExitButton.png"));

            /* Create buttons **/


            /* Create all other renderables **/
            renderableLayers.get(0).add(new ImageContainer(150, 75, background, 0));
            renderableLayers.get(0).add(new ImageContainer(750, 450, vendorImage, 0));


            /* Render item images into bear's inventory **/
            int itemCount = bearInventory.size();
            int k = 0;
            for (int yValItem1 : yValItems) {
                for (int xValBearItem : xValBearItems) {
                    if (k < itemCount) {
                        BufferedImage item = ImageIO.read(getClass().getResource(bearInventory.get(k)));
                        renderableLayers.get(1).add(new ImageContainer(xValBearItem, yValItem1, item, 1));
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
                        BufferedImage item = ImageIO.read(getClass().getResource(vendorInventory.get(k)));
                        renderableLayers.get(1).add(new ImageContainer(xValVendorItem, yValItem, item, 1));
                        k++;
                    }
                }
            }

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

    }
}
