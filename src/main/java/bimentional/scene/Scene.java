package bimentional.scene;

import bimentional.Camera;

public abstract class Scene {
  protected Camera camera;

  public Scene() {
  }

  public abstract void update(float dt);

  public abstract void init();
}
