package control;

import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    public static Map<String, Map<String, String>> AssetPathMap = new HashMap<>();

    public AssetManager(){

        AssetPathMap.put("Background",putBackgroundPaths());
        AssetPathMap.put("Button",putButtonPaths());
        AssetPathMap.put("Player",putPlayerPaths());
        AssetPathMap.put("ItemIcon",putItemIconPaths());

        //TODO: AssetPathMap.put("???",put???Paths());

    }

    //region<AssetPathMap Setup>
    private Map<String, String> putBackgroundPaths(){

        Map<String, String> backgrounds = new HashMap<>();
        backgrounds.put("BlackCover", "/assets/backgrounds/BG-BlackCover.png");
        //TODO: backgrounds.put("???", "/assets/backgrounds/???.png");
        return backgrounds;
    }

    private Map<String, String> putButtonPaths(){

        Map<String, String> buttons = new HashMap<>();
        buttons.put("Test", "/assets/backgrounds/Button-Test.png");
        //TODO: buttons.put("???", "/assets/buttons/???.png");
        return buttons;
    }

    private Map<String, String> putPlayerPaths(){

        Map<String, String> player = new HashMap<>();
        //TODO: player.put("???", "/assets/player/???.png");
        return player;
    }

    private Map<String, String> putItemIconPaths(){

        Map<String, String> itemIcons = new HashMap<>();
        itemIcons.put("RedPotion", "/assets/Items/redPotionSmall.png");
        //TODO: buttons.put("???", "/assets/ItemIcons/???.png");
        return itemIcons;
    }

    //TODO: private Map<String, String> put???Paths(){}
    //endregion

    public Map<String, BufferedImage> getImages(String category, String[] content){
        //TODO: Add stuff so loading screen knows what the hell is going on, mutha fukr
        Map<String, BufferedImage> imageMap = new HashMap<>();
        try {
            for (String item : content) {
                BufferedImage image = RenderEngine.convertToARGB(ImageIO.read(getClass()
                        .getResource(AssetPathMap.get(category).get(item))));
                imageMap.put(item, image);
            }
        }catch(IOException e)  {
            Debug.error(DebugEnabler.ASSET_MANAGER,"Error: " + e.getMessage());
        }
        return imageMap;
    }
}
