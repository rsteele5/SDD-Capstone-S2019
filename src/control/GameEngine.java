package control;


public class GameEngine implements Runnable {

    private PhysicsEngine physicsEngine;
    private RenderingEngine renderingEngine;
    private HouseGenerator houseGenerator;
    private CharacterGenerator characterGenerator;

    private InputManager inputManager;
    private ScreenManager screenManager;

    public GameEngine(){
        houseGenerator = new HouseGenerator();
        physicsEngine = new PhysicsEngine();
        renderingEngine = new RenderingEngine();
        characterGenerator = new CharacterGenerator();

        inputManager = new InputManager();
        screenManager = new ScreenManager(houseGenerator.getHouse());
    }

    @Override
    public void run() {
        //TODO: stuff
    }
}
