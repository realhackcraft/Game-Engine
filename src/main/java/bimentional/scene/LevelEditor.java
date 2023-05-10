package bimentional.scene;

import bimentional.Camera;
import bimentional.GameObject;
import bimentional.Transform;
import bimentional.Window;
import bimentional.components.SpriteRenderer;
import bimentional.components.Spritesheet;
import org.joml.Vector2f;
import util.AssetPool;

import java.util.Objects;

public class LevelEditor extends Scene {
  Window window = Window.get();

  public LevelEditor() {
    window.r = 1f;
    window.g = 1f;
    window.b = 1f;
    window.a = 1f;
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
    loadResources();

    this.camera = new Camera(new Vector2f());

    Spritesheet sprites = AssetPool.getSpriteSheet("assets/spritesheets/spritesheet.png");

    GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
    obj1.addComponent(new SpriteRenderer(sprites.getSprite(6)));
    this.addGameObject(obj1);

    GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
    obj2.addComponent(new SpriteRenderer(sprites.getSprite(25)));
    this.addGameObject(obj2);
  }


  private void loadResources() {
    AssetPool.getShader("/shaders/default.glsl").use();

    AssetPool.addSpriteSheet("assets/spritesheets/spritesheet.png",
        new Spritesheet(Objects.requireNonNull(AssetPool.getTexture("assets/spritesheets/spritesheet.png")),
            16, 16, 26, 0));
  }
}
