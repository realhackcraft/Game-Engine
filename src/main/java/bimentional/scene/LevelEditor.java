package bimentional.scene;

import bimentional.Camera;
import bimentional.GameObject;
import bimentional.Transform;
import bimentional.Window;
import bimentional.components.SpriteRender;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditor extends Scene {
  public LevelEditor() {
    Window.get().r = 1f;
    Window.get().g = 1f;
    Window.get().b = 1f;
    Window.get().a = 1f;
  }

  @Override
  public void update(float dt) {
    for (GameObject gameObject : this.gameObjects) {
      gameObject.update(dt);
    }

    this.renderer.render();
  }

  @Override
  public void init() {
    this.camera = new Camera(new Vector2f());
    int xOffset = 10;
    int yOffset = 10;

    float totalWidth = (float) (600 - xOffset * 2);
    float totalHeight = (float) (300 - yOffset * 2);
    float sizeX = totalWidth / 100;
    float sizeY = totalHeight / 100;

    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        float xPos = x * sizeX + xOffset;
        float yPos = y * sizeY + yOffset;
        GameObject gameObject = new GameObject("Obj" + x + " " + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
        gameObject.addComponent(new SpriteRender(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
        this.addGameObject(gameObject);
      }
    }

    loadResources();
  }

  private void loadResources() {
    AssetPool.getShader("/shaders/default.glsl").use();
  }
}
