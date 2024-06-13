package game;

import buttons.buttonsFrame;
import controller.PlayerController;
import core.Position;
import core.Size;
import display.Camera;
import display.Display;
import entity.GameObject;
import entity.Player;
import input.KeyInput;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Display main;
    private KeyInput keyInput;
    private Camera camera;
    private buttonsFrame bFrame;

    private List<GameObject> gameObjects = new ArrayList<>();

    public Game(Size windowSize, int width, int height) {
        keyInput = new KeyInput();
        main = new Display(width, height,  keyInput);
        bFrame = new buttonsFrame(main.map);

        gameObjects = new ArrayList<>();
        camera = new Camera(windowSize);

        AddPlayer(new Position(100, 100));
    }

    public Camera getCamera() {
        return camera;
    }

    public void AddPlayer(Position pos) {
        Player player = new Player(pos, new PlayerController(keyInput));
        gameObjects.add(player);
        player.game = this;
        camera.focusOn(player);
        main.setPlayer(player);
    }

    public void update() {
        gameObjects.forEach(gameObjects -> gameObjects.update());
        camera.update(this);
    }

    public void render() {
        main.render(this);
    }
}
