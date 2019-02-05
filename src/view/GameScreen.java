package view;

public abstract class GameScreen {
    private ScreenState currentState = ScreenState.TransitionOn;
    private boolean popup = false;
    private boolean exclusivePopup = false;

    public boolean isPopup() {return popup;}
    public void setPopup(boolean p) {popup = p;}
    public boolean isExclusivePopup() {return exclusivePopup;}
    public void setExclusivePopup(boolean ep) { exclusivePopup = ep;}

    public boolean isActive(){
        //TODO:Finish this
        return false;
    }

    public void update(){}

    public void draw(){}

    public void exitScreen(){}

}
