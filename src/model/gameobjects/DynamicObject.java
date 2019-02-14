package model.gameobjects;

public abstract class DynamicObject extends RenderableObject {

    public DynamicObject(int x, int y) {
        super(x, y);
    }

    //private StateManager stateManager;
    //TODO: Make objects stateful

    public abstract void update();

}
