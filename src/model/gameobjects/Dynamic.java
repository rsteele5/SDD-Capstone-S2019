package model.gameobjects;

public abstract class Dynamic extends Renderable {

    public Dynamic(int x, int y) {
        super(x, y);
    }

    //private StateManager stateManager;
    //TODO: Make objects stateful

    public abstract void update();

}
