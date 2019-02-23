package gameobjects.renderables.items;

import gameobjects.renderables.RenderableObject;

import javax.swing.*;

import java.awt.*;

import static java.lang.Math.round;

public abstract class Item extends RenderableObject {
    String imagePath;
    String itemName;
    String itemType;
    String itemDescription;
    int value;

    int maxAttributeValue = 4;
    int increaseAttributeValue = 1;

    public Item(){
        super();
        imagePath = "";
        itemName = "";
        itemType = "";
        value = 0;
        itemDescription = "";
    }

    /*public Item(String imgPath, String name, String type, String description){
        imagePath = imgPath;
        itemName = name;
        itemType = type;
        itemDescription = description;
        setAttributes();
    }*/


    /* Getters */
    public String getImagePath() {
        return imagePath;
    }

    public String getItemName() {
        return itemName;
    }

    /*public String getType() {
        return itemType;
    }

    public int getValue() {
        return value;
    }

    public String getItemDescription() { return itemDescription; }*/

    /** Item attributes initializer **/
    public abstract void setAttributes();

    //desc is yes no on whether to draw the description
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc, int fontsize)
    {
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, fontsize);
        graphics.setFont(font);
        graphics.drawString(itemName, x, y+ fontsize-1);

        graphics.drawString("  Type: " + itemType + "\n ", x, y + 2*(fontsize-1));
        graphics.drawString(" Value: $" + value  + "\n ", x, y + 3*(fontsize-1));
        if (desc) {
            graphics.drawString(itemDescription, x, y + 4 * (fontsize-1));
        }

    }

    /** Called upon completion of a level to increase new items' attributes **/
    public void increaseMaxAttributeValue(int n) {
        increaseAttributeValue += n;
        maxAttributeValue += increaseAttributeValue;
    }

    /** Called when item is purchased from vendor **/
    public int depreciateItem(){
        return round((float)(value * (0.9)));
    }


}
