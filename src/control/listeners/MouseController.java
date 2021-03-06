package control.listeners;

import control.RenderEngine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        //Find all the objects at the mouse click
        ((RenderEngine) e.getSource()).getScreenManager()
                .clickEventAtLocation(e.getX(), e.getY());
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
