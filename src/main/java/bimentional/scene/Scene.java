package bimentional.scene;

import bimentional.Camera;
import bimentional.GameObject;
import bimentional.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Camera camera;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected Renderer renderer = new Renderer();
    private boolean isRunning = false;

    public Scene() {
    }

    public abstract void update(float dt);

    public void start() {
        for (GameObject gameObject : gameObjects) {
            gameObject.start();
        }
        isRunning = true;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        this.renderer.add(gameObject);
//        this.renderer.add(gameObject);
        if (isRunning) {
            gameObject.start();
        }
    }

    public abstract void init();

    public Camera getCamera() {
        return camera;
    }
}
