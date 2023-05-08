package bimentional.scene;

import bimentional.Camera;
import bimentional.GameObject;
import bimentional.Transform;
import bimentional.Window;
import bimentional.components.SpriteRenderer;
import org.joml.Vector2f;
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

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/textures/testImage2.png")));
        this.addGameObject(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/textures/testImage.png")));
        this.addGameObject(obj2);

        loadResources();
    }


    private void loadResources() {
        AssetPool.getShader("/shaders/default.glsl").use();
    }
}
