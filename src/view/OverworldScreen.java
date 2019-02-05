package view;

import java.util.concurrent.CopyOnWriteArrayList;

public class OverworldScreen extends GameScreen {

    private CopyOnWriteArrayList<Integer> house;

    public OverworldScreen(CopyOnWriteArrayList<Integer> h){
        house = h;
    }
}
