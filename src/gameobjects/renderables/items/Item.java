package gameobjects.renderables.items;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public interface Item {

    BufferedImage getIcon();
    String getItemName();
    String getDescription(boolean desc);
    ItemCategory getCategory();
    int getType();  //TODO: find best way of sending subtype
    int getValue();
    void depreciate();
}
