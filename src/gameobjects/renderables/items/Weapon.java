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

    protected Weapon(int x, int y, String imagePath, DrawLayer layer,
                  String name, int value, WeaponType type){
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;

        setDescription();
    }

    @Override
    public BufferedImage getIcon() {
        return null;
    }

    @Override
    public String getItemName() {
        return null;
    }

    @Override
    public String getDescription() {
        //Name: My name
        //Damage: 23-25
        return  "Name: " + name + "\n" +
                "Type: " + type.name(); //+ "\n" +
                //"Damage: " + maxDmg + "-" + minDmg;
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


