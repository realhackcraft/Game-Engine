package bimentional.components;

import bimentional.Transform;
import bimentional.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private final Vector4f color;
    private boolean isDirty = false;
    private Sprite sprite;
    private Transform lastTransform;

    @SuppressWarnings("unused")
    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(gameObject.transform)) {
            this.gameObject.transform.copyFrom(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void start() {
        super.start();
        this.lastTransform = gameObject.transform.copy();
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color.set(color);
            isDirty = true;
        }
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public final Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        isDirty = true;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void clean() {
        isDirty = false;
    }
}
