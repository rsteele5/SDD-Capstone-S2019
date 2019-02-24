package gameobjects.renderables.items;

import java.awt.image.BufferedImage;

public interface Item {

    BufferedImage getIcon();
    String getItemName();
    String getDescription();
    ItemCategory getCategory();
    int getType();  //TODO: find best way of sending subtype
    int getValue();
}
