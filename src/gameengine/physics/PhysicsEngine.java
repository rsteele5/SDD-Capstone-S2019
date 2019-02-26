package gameengine.physics;

import gameobjects.Player;
import gamescreens.ScreenManager;
import gameobjects.GameObject;
import main.utilities.Debug;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class PhysicsEngine {
    ScreenManager screenManager;
    final int winWidth = 1280;
    final int winheight = 720;
    //Rectangle boundaries = new Rectangle(0,0,1280,720);
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

                if ((obj.getY() + ((Kinematic) obj).getHitbox().height) > winheight) {
                    obj.setY(winheight - ((Kinematic) obj).getHitbox().height);
                    ((Kinematic) obj).setAcceleration(new PhysicsVector(0,1));
                }

                if ((obj.getX() + ((Kinematic) obj).getHitbox().width) > winWidth) {
                    obj.setX(winWidth - ((Kinematic) obj).getHitbox().width);
                    ((Kinematic) obj).setAcceleration(new PhysicsVector(0,1));
                }

                if (obj.getX() < 0) {
                    obj.setX(0);
                }

                if (obj.getY() < 0) {
                    obj.setY(0);
                }
                /*
                if(!((Kinematic) objects.get(i1)).getHitbox().intersects(boundaries)){
                    ((Kinematic) obj).setAcceleration(0);
                    Debug.log(true,obj + "");
                    Rectangle intersect = ((Kinematic) obj).getHitbox().intersection(boundaries);
                    obj.setY((int)intersect.getY() + intersect.height - ((Kinematic) obj).getHitbox().height);
                }
                */
            }
            for(int i2 = i1; i2 < indices - 1; i2++) {
                if(objects.get(i2) instanceof Kinematic && objects.get(i2 + 1) instanceof Kinematic) {
                    GameObject obj1 = (GameObject) objects.get(i2);
                    GameObject obj2 = (GameObject) objects.get(i2 + 1);
                    if(((Kinematic)obj1).getHitbox().intersects(((Kinematic)obj2).getHitbox())){
                        ((Kinematic) obj1).setAcceleration(new PhysicsVector(0,1));
                        Rectangle intersect = ((Kinematic) obj1).getHitbox().intersection(((Kinematic)obj2).getHitbox());
                        obj1.setY(obj1.getY() - intersect.height);
                    }
                }
            }
        }
    }

}
