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
    isRunning = true;
    for (GameObject gameObject : gameObjects) {
      gameObject.start();
      this.renderer.add(gameObject);
    }
  }

  public void addGameObject(GameObject gameObject) {
    gameObjects.add(gameObject);
    if (!isRunning) {
      isRunning = true;
      gameObject.start();
      this.renderer.add(gameObject);
    }
  }

  public abstract void init();

  public Camera getCamera() {
    return camera;
  }
}
