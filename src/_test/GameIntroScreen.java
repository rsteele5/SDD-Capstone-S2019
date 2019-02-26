package _test;

import gameobjects.renderables.DialogBox;
import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;

import java.awt.*;

public class GameIntroScreen extends GameScreen {

    public GameIntroScreen(ScreenManager screenManager) {
        super(screenManager, "TestScreen", true);
    }

    private final String text = "Arise!...\n\n " +
            "Your child needs your help!\n\n" +
            "Once again the nightmarish horrors seek to destroy that which you hold dear!\n" +
            "Destroy them like so many countless times before\n\n" +
            "Arise!. . . to arms brave teddy! TO ARMS!";

    @Override
    protected void initializeScreen() {

        ImageContainer cover = new ImageContainer(0,0, "/assets/backgrounds/BG-BlackCover.png", DrawLayer.Background);
        cover.setAlpha(1f);
        cover.addToScreen(this, true);

        DialogBox diagBox = new DialogBox(320,180, 640, 360, text,
                new Font("NoScary", Font.PLAIN, 40), Color.WHITE);
        diagBox.addToScreen(this, true);
    }
}
