package gameobjects;

import main.utilities.Action;


public interface Clickable {
    void onClick();
    void setOnClick(Action onClick);
    boolean contains(int x, int y);
}
