package bimentional.scene;

import bimentional.*;
import bimentional.components.SpriteRenderer;
import bimentional.components.Spritesheet;
import org.joml.Vector2f;
import util.AssetPool;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditor extends Scene {
    Window window = Window.get();

    private GameObject obj1;
    private GameObject obj2;

    public LevelEditor() {
        window.r = 0f;
        window.g = 0f;
        window.b = 0f;
        window.a = 1f;
    }

    @Override
    public void update(float dt) {
//        === Movement TEST ===
        float speed;

        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            speed = 100f;
        } else {
            speed = 50f;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
                obj1.transform.position.x += speed * dt;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
                obj1.transform.position.x -= speed * dt;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
                obj1.transform.position.y += speed * dt;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
                obj1.transform.position.y -= speed * dt;
            }
        } else {
            if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
                obj2.transform.position.x += speed * dt;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
                obj2.transform.position.x -= speed * dt;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
                obj2.transform.position.y += speed * dt;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
                obj2.transform.position.y -= speed * dt;
            }
        }

        super.update(dt);
    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f());

        Spritesheet sprites = AssetPool.getSpriteSheet("assets/spritesheets/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(6)));
        this.addGameObject(obj1);

        obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(25)));
        this.addGameObject(obj2);

        this.renderer.render(true);
    }


    private void loadResources() {
        AssetPool.getShader("/shaders/default.glsl").use();

        AssetPool.addSpriteSheet("assets/spritesheets/spritesheet.png",
                new Spritesheet(Objects.requireNonNull(AssetPool.getTexture("assets/spritesheets/spritesheet.png")),
                        16, 16, 26, 0));
    }
}
