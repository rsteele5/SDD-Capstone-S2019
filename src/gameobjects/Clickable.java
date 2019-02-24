package gameobjects;

import gamescreens.ScreenManager;

import java.util.function.Consumer;

public interface Clickable {
    void onClick(ScreenManager screenManager);
    void setOnClick(Consumer<ScreenManager> onClick);
    boolean contains(int x, int y);
}
