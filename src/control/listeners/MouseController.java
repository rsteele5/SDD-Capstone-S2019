package control.listeners;

import model.gameobjects.GameObject;
import model.gameobjects.Renderable;
import model.gameobjects.buttons.Button;
import utilities.Log;
import view.renderengine.RenderEngine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class MouseController implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        //Find all the objects at the mouse click
        Log.logSuccess("Clicked!");
        CopyOnWriteArrayList<Renderable> gameObjects =
                ((RenderEngine) e.getSource())
                        .getScreenManager()
                        .getObjectsAtLocation(e.getX(), e.getY());
        Log.logSuccess("Number of objects at location: " + gameObjects.size());
        //Find the first clickable object in the list and click it
        for(Renderable renderable: gameObjects) {
            if(renderable instanceof Button){
                Log.logSuccess("Theres a button here");
                ((Button) renderable).onClick(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
