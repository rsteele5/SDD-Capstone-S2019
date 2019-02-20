package gameobjects.renderables.items;

import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;

public abstract class Item extends RenderableObject {
    protected String imagePath;
    protected String itemName;
    protected String type;
    protected int damage;
    protected int immunity;
    protected int critChance;
    protected int value;
    protected String description1;
    protected String description2;

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    protected Button button;

    public Item(){
        super();
        imagePath = "";
        itemName = "";
        type = "";
        immunity = 0;
        critChance = 0;
        value = 0;
        // No text wrap yet. Max of 28 characters per line
        description1 = "";
    }


    /* Getters */
    public String getImagePath() {
        return imagePath;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public int getDamage() { return damage;}

    public int getImmunity() {
        return immunity;
    }

    public int getCritChance() {
        return critChance;
    }

    public int getValue() {
        return value;
    }

    public String getDescription1() { return description1; }

    public String getDescription2() { return description2; }


    @Override
    public void update() {

    }
}
