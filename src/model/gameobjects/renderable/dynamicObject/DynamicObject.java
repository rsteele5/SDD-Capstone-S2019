package model.gameobjects.renderable.dynamicObject;

public interface DynamicObject<T> {

    public void setState(T t);
    public T getState();
    public void nextState();

}
