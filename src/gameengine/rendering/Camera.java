package gameengine.rendering;

import gameobjects.GameObject;
import gameobjects.renderables.RenderableObject;
import gamescreens.GameScreen;

import java.awt.*;

public class Camera {

    public GameScreen screen;
    public GameObject target;

    public Camera(GameScreen gameScreen, GameObject target) {
        this.screen = gameScreen;
        this.target = target;
    }

    public void track(Graphics2D graphics) {
        graphics.translate(-target.getX()+640, -target.getY()+360);
        for(RenderableObject renderableObject: screen.getRenderables()){
            renderableObject.draw(graphics);
        }
        graphics.translate(target.getX()-640, target.getY()-360);
    }

    public void setTarget(GameObject target){
        this.target = target;
    }
}
