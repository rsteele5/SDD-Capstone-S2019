package gameobjects;

import gamescreens.GameScreen;

import java.util.function.Consumer;

public interface Clickable {
    void onClick(GameScreen gameScreen);
    void setOnClick(Consumer<GameScreen> onClick);
    boolean contains(int x, int y);
}
