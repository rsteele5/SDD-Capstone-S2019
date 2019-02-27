package gameengine.physics;

import gameobjects.Player;
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

                //Gravity Application
                if ((((Kinematic) obj).getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                    ((Kinematic) obj).setAcceleration(((Kinematic) obj).getAcceleration().add(new PhysicsVector(0,PhysicsMeta.Gravity)));

//                if(obj instanceof Player && ((((Player) obj).mov() & 0b10) == 0b010 || (((Player) obj).mov() & 0100) == 0b1)){
//                    ((Player) obj).setAcceleration(((Player) obj).getAcceleration().get);
//                }

                //Setting Objects
                obj.setY(obj.getY() + (int) ((Kinematic) obj).getVelocity().y);
                obj.setX(obj.getX() + (int) ((Kinematic) obj).getVelocity().x);

                //Screen Collision Detection
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
                    if (obj.getX() < 0) obj.setX(0);
                    if (obj.getY() < 0) obj.setY(0);
                }
            }

            //Collision Detection
            for(int i2 = 0; i2 < indices - 1; i2++) {
                if(i2 == i1) continue;
                if(objects.get(i1) instanceof Kinematic && objects.get(i2) instanceof Kinematic) {
                   // if(objects.get(i2) instanceof  Player) Debug.log(true, "X: " + objects.get(i2).getHitbox().x + " width : " + objects.get(i2).getHitbox().width);
                    GameObject obj1 = (GameObject) objects.get(i1);
                    GameObject obj2 = (GameObject) objects.get(i2);
                    if(((Kinematic)obj1).getHitbox().intersects(((Kinematic)obj2).getHitbox())){
                        //if(objects.get(i2) instanceof  Player) Debug.log(true,"HERE");
                        if(obj1  instanceof Player) ((Player) obj1).grounded = true;
                        ((Kinematic) obj1).setAcceleration(new PhysicsVector(1,1));
                        Rectangle intersect = ((Kinematic) obj1).getHitbox().intersection(((Kinematic)obj2).getHitbox());
                        obj1.setY(obj1.getY() - intersect.height);
                        i2 = 0;
                    }
                }
            }
        }
    }

}
