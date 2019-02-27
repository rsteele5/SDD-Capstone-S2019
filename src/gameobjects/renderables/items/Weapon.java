package gameobjects.renderables.items;

import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;
import main.utilities.AssetLoader;

import java.awt.image.BufferedImage;

public class Weapon extends RenderableObject implements Item {
    // Item Variables
    protected BufferedImage icon;
    protected String name;
    protected String description;
    protected int value;

    // Weapon Variables
    protected WeaponType type;
    protected int maxDamage;
    protected int minDamage;
    protected int critChance;

    protected Weapon(int x, int y, String imagePath, DrawLayer layer,
                     String name, int value, WeaponType type, int minDamage, int maxDamage, int critChance){
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.critChance = critChance;

        setDescription("Testing the weapon description field now. aa aa aa aa aa aa aa aa aa aa aa aa stop");
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String getDescription() {
        return  name +
                "\nType: " + type.name() +
                "\nDamage: " + minDamage + "-" + maxDamage +
                "\nCrit Chance: " + critChance + "%" +
                "\nValue: " + value + " gold" +
                description;
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Weapon;
    }

    @Override
    public int getType() {
        return type.ordinal();
    }

    @Override
    public int getValue() {
        return value;
    }

    public int getMinDamage() { return minDamage;}

    public int getMaxDamage() { return maxDamage;}

    public int getCritChance() { return critChance;}

    private void setDescription(String myDescription) {
        description = "\n" + myDescription;
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


