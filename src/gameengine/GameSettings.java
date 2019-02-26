package gameengine;

import java.awt.*;
import static gameengine.GameSettings.InputMethod.*;
import static gameengine.GameSettings.GraphicsOption.*;
import static gameengine.GameSettings.PlayerOption.*;

public class GameSettings {

    private static GameEngine gameEngine;

    private static InputMethod inputMethod;
    private static GraphicsOption graphicsOption;
    private static PlayerOption playerOptions;
    private static SoundOption soundOption;

    public GameSettings(GameEngine gameEngine) {
        GameSettings.gameEngine = gameEngine;
        inputMethod = KeyBoard;
        graphicsOption = High;
        playerOptions = Solo;
    }

    public enum InputMethod {
        KeyBoard,
        GamePad
    }

    public enum GraphicsOption {
        High,
        Medium,
        Low
    }

    public enum PlayerOption {
        Solo,
        Coop
    }

    public enum SoundOption {
        On,
        Off
    }

    public InputMethod getInputMethod() {
        return inputMethod;
    }

    public void setIputMethod(InputMethod inputMethod) {
        this.inputMethod = inputMethod;
        gameEngine.changeInputMethod(inputMethod);
    }

    public GraphicsOption getGraphicsOption() {
        return graphicsOption;
    }

    public void setGraphicsOption(GraphicsOption graphicsOption) {
        this.graphicsOption = graphicsOption;
        gameEngine.changeGraphicsOption(graphicsOption);
    }

    public SoundOption getSoundOption() {
        return soundOption;
    }

    public void setSoundOption(SoundOption soundOption) {
        this.soundOption = soundOption;
        gameEngine.changeSoundOption(soundOption);
    }

    public Graphics getGraphics() {
        return gameEngine.getGraphics();
    }
}
