package gameobjects.renderables.items;

import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;
import main.utilities.AssetLoader;
import static gamescreens.DrawLayer.Effects;
import static gameobjects.renderables.items.ArmorType.Head;
import static gameobjects.renderables.items.ArmorType.Chest;
import static gameobjects.renderables.items.ArmorType.Pants;
import static gameobjects.renderables.items.ArmorType.Feet;
import static gameobjects.renderables.items.ArmorType.OffHand;
import static gamescreens.DrawLayer.Background;
import static gamescreens.DrawLayer.Scenery;

import java.awt.image.BufferedImage;

public class Armor extends RenderableObject implements Item{

    // Item Variables
    protected BufferedImage icon;
    protected String name;
    protected String description;
    protected int value;

    // Armor Variables
    protected int armorValue;
    //protected FundamentalProperty;
    //TODO check on mass with austion/hunter
    //protected int mass;
    protected ArmorType type;

    protected Armor(int x, int y, String imagePath, DrawLayer layer,
                     String name, int value, ArmorType type) {
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;

        setDescription();
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public String getItemName() {
        return null;
    }

    @Override
    public String getDescription() {
        //TODO make this general for all armor
        return  "Name: " + name + "\n" +
                "Type: " + type.name()+ "\n" +
                "Armor Points: " + 12 + "\n" +
                "Description: \nThis Armor is pretty not dope. I found it in a dumpster" + "\n" +
                "It is basically paper." + "\n" +
                "I'd replace it as soon as possible"  + "\n" +
                "Keep it well my friend";
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Armor;
    }

    @Override
    public int getType() {
        return type.ordinal();
    }

    @Override
    public int getValue() {
        return 0;
    }

    private void setDescription() {
        //TODO: Parse a String to show up in a text box
    }

    @Override
    public void update() {

    }

    @Override
    public void load() {
        super.load();
        icon = AssetLoader.resizeImage(image, image.getWidth()/2, image.getHeight()/2);
    }
}
