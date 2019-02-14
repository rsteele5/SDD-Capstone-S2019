package _test.splashscreentest;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.RenderableObject;
import model.levels.LevelData;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestLevelData extends LevelData {

    private final String name = "TestLevelData";

    //TODO: Flesh out this class
    @Override
    public void update() {
        //TODO: Send update down the chain to objects
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        for(RenderableObject renderable: getAllRenderables()){
            Debug.success(DebugEnabler.RENDERABLE_LOG, name+" Draw");
            renderable.draw(graphics2D);
        }
    }

    @Override
    public void loadObjects(ScreenManager screenManager) {
        try {
            initializeLayers();
            int objectsLoaded = 0;

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Content");
//            //Background
//            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource("/assets/backgrounds/BG-BlackCover.png")));
//            getRenderableLayers().get(0).add(new ImageContainer(0,0, background, 0));

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Teddy");
            //Foreground
            BufferedImage teddy = ImageIO.read(getClass().getResource("/assets/gameObjects/Teddy.png"));
            getRenderableLayers().get(1).add(new ImageContainer(100, 500, teddy, 1));
            screenManager.updateLoadingScreen(++objectsLoaded);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Eye");
            //Foreground
            BufferedImage eye = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource("/assets/gameObjects/StabbyEye.png")));
            getRenderableLayers().get(1).add(new ImageContainer(1000, 500, eye, 1));
            screenManager.updateLoadingScreen(++objectsLoaded);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Eye");
            //Foreground
            getRenderableLayers().get(1).add(new ImageContainer(1000, 500-100, eye, 1));
            screenManager.updateLoadingScreen(++objectsLoaded);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Eye");
            //Foreground
            getRenderableLayers().get(1).add(new ImageContainer(1000-100, 500-100, eye, 1));
            screenManager.updateLoadingScreen(++objectsLoaded);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Eye");
            //Foreground
            getRenderableLayers().get(1).add(new ImageContainer(1000-100, 500, eye, 1));
            screenManager.updateLoadingScreen(++objectsLoaded);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name+"-Loading Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error loading image: " + e.getMessage());
        }
    }

    @Override
    public int getLoadData() {
        return 5;
    }

    @Override
    public void initializeLayers() {
        Debug.log(DebugEnabler.RENDERABLE_LOG, name+" Initializing Layers");
        renderableLayers = new CopyOnWriteArrayList<>();
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }
}
