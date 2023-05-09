package bimentional.components;

import bimentional.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

  private final Vector4f color;
  private final Sprite sprite;

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
  }

  @Override
  public void start() {
    super.start();
  }

  public Vector4f getColor() {
    return color;
  }

  public Texture getTexture() {
    return sprite.getTexture();
  }


  public final Vector2f[] getTexCoords() {
    return sprite.getTexCoords();
  }
}
