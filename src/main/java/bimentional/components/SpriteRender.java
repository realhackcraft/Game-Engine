package bimentional.components;

import org.joml.Vector4f;

public class SpriteRender extends Component {

  private final Vector4f color;

  public SpriteRender(Vector4f color) {
    this.color = color;
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
}
