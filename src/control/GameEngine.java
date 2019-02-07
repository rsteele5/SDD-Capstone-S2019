package control;


import view.RenderEngine;

public class GameEngine implements Runnable {

    private PhysicsEngine physicsEngine;
    private RenderEngine renderEngine;
    private HouseGenerator houseGenerator;
    private CharacterGenerator characterGenerator;

    private InputManager inputManager;

    public GameEngine(){
        houseGenerator = new HouseGenerator();
        physicsEngine = new PhysicsEngine();
        characterGenerator = new CharacterGenerator();

        inputManager = new InputManager();
        renderEngine = new RenderEngine(houseGenerator.getHouse());
    }

    @Override
    public void run() {
        //TODO: stuff
        while(true){
            renderEngine.gameRender();
        }
    }
}
