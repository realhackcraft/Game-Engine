package bimentional.components;

import bimentional.GameObject;

public abstract class Component {
  public boolean started = false;
  protected GameObject gameObject = null;

  public abstract void update(float dt);

  public GameObject getGameObject() {
    return gameObject;
  }

  public void setGameObject(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  public void start() {
    started = true;
  }

  public boolean started() {
    return started;
  }
}
