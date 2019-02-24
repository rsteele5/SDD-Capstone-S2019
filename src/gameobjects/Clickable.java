package gameobjects;

import gamescreens.ScreenManager;

import java.util.function.Consumer;

public interface Clickable<T> {
    void onClick(T thing);
    void setOnClick(Consumer<T> onClick);
    boolean contains(int x, int y);
}
