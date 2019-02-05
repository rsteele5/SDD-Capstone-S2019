package control;

import view.GameScreen;
import view.OverworldScreen;
import view.TitleScreen;

import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenManager {
    CopyOnWriteArrayList<GameScreen> gameScreens;

    ScreenManager(CopyOnWriteArrayList<Integer> house){
        gameScreens = new CopyOnWriteArrayList<>();
        gameScreens.add(new TitleScreen());
        gameScreens.add(new OverworldScreen(house));
    }
}
