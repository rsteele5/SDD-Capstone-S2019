package main;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import main.utilities.Debug;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import gameengine.GameEngine;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameobjects.Player;
import gamescreens.GameScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.swing.*;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    public GameWindow() {
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Player Controller management.
        //TODO: Implement multiple players.
        Player p1 = GameEngine.players.get(0);
        if(p1.playerState == Player.State.awake) {
            /*
            0b1       =   right
            0b10      =   left
            0b100     =   space
            */
            int flags = p1.mov();
            flags += e.getKeyCode() == 68 && ((flags & 0b1) == 0)   ? 0b1   : 0; /*d right*/
            flags += e.getKeyCode() == 65 && ((flags & 0b10) == 0)  ? 0b10  : 0; /*a left*/
           // flags += e.getKeyCode() == 32 && ((flags & 0b100) == 0) && p1.grounded ? 0b100 : 0; /*space up*/
            if((e.getKeyCode() & 32) == 32 && p1.grounded ){
                p1.setAcceleration(p1.getAcceleration().add(new PhysicsVector(0,-7)));
                p1.grounded = false;
                //flags -= 0b100;
            }
            p1.setMov(flags);
            int x = 0b1 & flags;
            x +=  (((0b10 & flags) / 0b10) * -1);
            p1.setVelocity(new PhysicsVector(x,0));
            //p1.setAcceleration(new PhysicsVector(0,((0b100 & flags)/ 0b100)*-5));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player p1 = GameEngine.players.get(0);
        if(p1.playerState == Player.State.awake) {
            int flags = p1.mov();
            flags -= e.getKeyCode() == 68 && ((flags & 0b1) == 0b1)     ? 0b1   : 0;
            flags -= e.getKeyCode() == 65 && ((flags & 0b10) == 0b10)   ? 0b10  : 0;
            //flags -= e.getKeyCode() == 32 && ((flags & 0b100) == 0b100) ? 0b100 : 0;
            p1.setMov(flags);
            int x = 0b1 & flags;
            x +=  (((0b10 & flags) / 0b10) * -1);
            p1.setVelocity(new PhysicsVector(x,/*((0b100 & flags)/ 0b100)*-1*/0));

        }
    }
}


