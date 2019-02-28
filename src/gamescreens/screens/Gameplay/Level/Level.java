package gamescreens.screens.Gameplay.Level;

import gameobjects.Player;
import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;

import java.util.ArrayList;

public class Level {
    protected ImageContainer background;

    public void setBackground(String fPath) {
        this.background = new ImageContainer(0, 0, fPath, DrawLayer.Background);
    }

}
