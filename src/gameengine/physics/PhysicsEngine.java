package gameengine.physics;

import gameengine.GameEngine;
import gameobjects.Player;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.items.Weapon;
import gamescreens.ScreenManager;
import gameobjects.GameObject;
import main.Game;
import main.utilities.Debug;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsEngine {
    ScreenManager screenManager;
    final int winWidth = Game.WIN_WIDTH;
    final int winHeight = Game.WIN_HEIGHT;
    //Collide with edges of Screen?

    public PhysicsEngine(ScreenManager myScreenManager){
        screenManager = myScreenManager;
    }

    public void update(){
        ArrayList<Kinematic> objects;
        objects = screenManager.getPhysicsObjects();
        if(objects == null) return;
        int indices = objects.size();
        for(int i1 = 0; i1 < indices; i1++){
            if(objects.get(i1) instanceof Kinematic) {
                GameObject obj = (GameObject) objects.get(i1);

                if ((((Kinematic) obj).getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                    ((Kinematic) obj).setAcceleration(((Kinematic) obj).getAcceleration().add(new PhysicsVector(0,PhysicsMeta.Gravity)));

                obj.setY(obj.getY() + (int) ((Kinematic) obj).getVelocity().y);
                obj.setX(obj.getX() + (int) ((Kinematic) obj).getVelocity().x);

                if(PhysicsMeta.boundaries) {
                    if ((obj.getY() + ((Kinematic) obj).getHitbox().height) > winHeight) {
                        obj.setY(winHeight - ((Kinematic) obj).getHitbox().height);
                        ((Kinematic) obj).setAcceleration(new PhysicsVector(1, 1));
                        if(obj instanceof Player) ((Player) obj).grounded = true;
                    }
                    if ((obj.getX() + ((Kinematic) obj).getHitbox().width) > winWidth) {
                        obj.setX(winWidth - ((Kinematic) obj).getHitbox().width);
                        ((Kinematic) obj).setAcceleration(new PhysicsVector(1, 1));
                    }
                    if (obj.getX() <= 0) obj.setX(0);
                    if (obj.getY() <= 0) {
                        obj.setY(0);
                        if(PhysicsMeta.AntiGravity && obj instanceof Player) ((Player) obj).grounded = true;
                    };
                }
            }

            //Collision Detection
            for(int i2 = 0; i2 < indices - 1; i2++) {
                if(i2 == i1) continue;
                if(objects.get(i2) instanceof Kinematic) {
                    GameObject obj1 = (GameObject) objects.get(i1);
                    GameObject obj2 = (GameObject) objects.get(i2);
                    if(((Kinematic)obj1).getHitbox().intersects(((Kinematic)obj2).getHitbox())){
                        if(obj1  instanceof Player) ((Player) obj1).grounded = true;
                        if(obj2  instanceof Player) ((Player) obj2).grounded = true;
                        if (obj1 instanceof Weapon && obj2 instanceof Player)  {
                            GameEngine.players.get(0).addItem((Weapon) obj1);
                            Debug.log(true, "Weapon Get!");
                        }
                        ((Kinematic) obj1).setAcceleration(new PhysicsVector(1,1));
                        ((Kinematic) obj2).setAcceleration(new PhysicsVector(1,1));
                        Rectangle intersect = ((Kinematic) obj1).getHitbox().intersection(((Kinematic)obj2).getHitbox());
                        int signX = obj1.getX() < obj2.getX() + ((Kinematic) obj2).getHitbox().width/2 ? -1 : 1;
                        int signY = obj1.getY() < obj2.getY() + ((Kinematic) obj2).getHitbox().height/2 ? -1 : 1;
                        if(intersect.height > .5 && intersect.width > .5) {
                            if (intersect.height < intersect.width) {
                                obj1.setY(obj1.getY() + (int) (intersect.height / 2 + 1) * signY);
                                obj2.setY(obj2.getY() - (intersect.height / 2 + 1) * signY);
                            }
                            else {
                                obj1.setX(obj1.getX() + (intersect.width / 2 + 1) * signX);
                                obj2.setX(obj2.getX() - (intersect.width / 2 + 1) * signX);
                            }
                        }
                        i2 = 0;
                    }
                }
            }
        }
    }

}
