package control.listeners;

import java.awt.event.MouseEvent;

public interface Clickable {

    boolean clicked = false;

    void onClick(MouseEvent e);

}
