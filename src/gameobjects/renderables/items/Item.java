package gameobjects.renderables.items;

import java.awt.image.BufferedImage;

public interface Item {

    BufferedImage getIcon();
    String getItemName();
    String getDescription();
    int getValue();
}
