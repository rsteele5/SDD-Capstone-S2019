package control.physics;

import _test.splashscreentest.Square;
import control.ScreenManager;
import model.gameobjects.GameObject;
import utilities.Debug;

import java.awt.*;
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
        CopyOnWriteArrayList<GameObject> objects = screenManager.getLevelData().levelObjects();
        int indices = objects.size();
        for(int i1 = 0; i1 < indices; i1++){
            if(objects.get(i1) instanceof Kinematic) {
                GameObject obj = objects.get(i1);

                if((((Kinematic) obj).getAcceleration() + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity )
                    ((Kinematic) obj).setAcceleration(((Kinematic) obj).getAcceleration() + PhysicsMeta.Gravity);

                obj.setY(obj.getY() + (int)((Kinematic) obj).getVelocity().y);

                if ((obj.getY() + ((Kinematic) obj).getHitbox().height) > winheight) {
                    obj.setY(winheight - ((Kinematic) obj).getHitbox().height);
                }

                if ((obj.getX() + ((Kinematic) obj).getHitbox().width) > winWidth) {
                    obj.setX(winWidth - ((Kinematic) obj).getHitbox().width);
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
                    GameObject obj1 = objects.get(i2);
                    GameObject obj2 = objects.get(i2 + 1);
                    if(((Kinematic)obj1).getHitbox().intersects(((Kinematic)obj2).getHitbox())){
                        ((Kinematic) obj1).setAcceleration(1);
                        Rectangle intersect = ((Kinematic) obj1).getHitbox().intersection(((Kinematic)obj2).getHitbox());
                      //  Debug.log(true, ((Square)obj1).description());
                        obj1.setY(obj1.getY() - intersect.height);
                    }
                }
            }
        }
    }

}
