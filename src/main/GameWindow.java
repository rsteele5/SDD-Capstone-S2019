package main;

import gameengine.GameEngine;
import gameengine.physics.PhysicsVector;
import gameobjects.Player;

import javax.swing.*;
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
        int flags;
        switch (p1.playerState){
            case asleep:
                break;

            case sideScroll:
                 /*
                0b1       =   right
                0b10      =   left
                */
                flags = p1.mov();
                flags += e.getKeyCode() == 68 && ((flags & 0b1) == 0)   ? 0b1   : 0; /*d right*/
                flags += e.getKeyCode() == 65 && ((flags & 0b10) == 0)  ? 0b10  : 0; /*a left*/
                if((e.getKeyCode() & 32) == 32 && p1.grounded ){
                    p1.setAcceleration(p1.getAcceleration().add(new PhysicsVector(0,-7)));
                    p1.grounded = false;
                }
                p1.setMov(flags);
                int x = 0b1 & flags;
                x +=  (((0b10 & flags) / 0b10) * -1);
                p1.setVelocity(new PhysicsVector(x,0));
                break;

            case overWorld:
                /*
                0b1       =   right
                0b10      =   left
                0b100     =   up
                0b1000    =   down
                */
            //    flags = p1.mov();
           //     flags += e.getKeyCode() == 68 && ((flags & 0b1) == 0)   ? 0b1   : 0; /*d right*/
            //    flags += e.getKeyCode() == 65 && ((flags & 0b10) == 0)  ? 0b10  : 0; /*a left*/
             //   flags += e.getKeyCode() == 68 && ((flags & 0b100) == 0)   ? 0b100   : 0; /*s down*/
              //  flags += e.getKeyCode() == 65 && ((flags & 0b1000) == 0)  ? 0b1000  : 0; /*w up*/

                break;
        }
        if(p1.playerState == Player.PlayerState.sideScroll) {

        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        Player p1 = GameEngine.players.get(0);
        switch (p1.playerState){
            case asleep:
                break;

            case sideScroll:
                int flags = p1.mov();
                flags -= e.getKeyCode() == 68 && ((flags & 0b1) == 0b1) ? 0b1 : 0;
                flags -= e.getKeyCode() == 65 && ((flags & 0b10) == 0b10) ? 0b10 : 0;
                p1.setMov(flags);
                int x = 0b1 & flags;
                x += (((0b10 & flags) / 0b10) * -1);
                p1.setVelocity(new PhysicsVector(x,/*((0b100 & flags)/ 0b100)*-1*/0));

                break;

            case overWorld:
                break;
        }
    }
}


