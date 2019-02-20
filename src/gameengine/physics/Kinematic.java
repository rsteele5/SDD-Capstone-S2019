package control.physics;

import java.awt.*;

public interface Kinematic {
   PhysicsVector getVelocity();
   double getAcceleration();
   void setAcceleration(double d);
   Rectangle getHitbox();
}
