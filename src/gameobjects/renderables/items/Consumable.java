package gameobjects.renderables.items;

import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;
import main.utilities.AssetLoader;

import java.awt.image.BufferedImage;

public class Consumable extends RenderableObject implements Item {

    // Item Variables
    protected BufferedImage icon;
    protected String name;
    protected String description;
    protected int value;

    // Consumable variables
    protected AffectType affect;
    protected ConsumableType type;
    protected int minAffect;
    protected int maxAffect;

    // TODO: Consume function should accept player as argument. Then does stuff to player depending on the consumable


    protected Consumable(int x, int y, String imagePath, DrawLayer layer,
                         String name, int value, ConsumableType type,
                         AffectType affect, int maxAffect, int minAffect){
        super(x, y, imagePath, layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.affect = affect;
        this.maxAffect = maxAffect;
        this.minAffect = minAffect;

        setDescription("Testing the consumable description field now. aaaaaaaaa aaaaaaaaaaa aaaaaaaaa aaaaaaa");

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
        return name +
                "\nType: " + type.name() +
                "\nAffect: " + affect.name() +
                "\nEffectiveness: " + minAffect + "-" + maxAffect +
                description;
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Consumable;
    }

    @Override
    public int getType() {
        return type.ordinal();
    }

    public int getAffect() {
        return affect.ordinal();
    }

    @Override
    public int getValue() {
        return value;
    }

    public int getMinAffect() { return minAffect;}

    public int getMaxAffect() { return maxAffect;}

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
