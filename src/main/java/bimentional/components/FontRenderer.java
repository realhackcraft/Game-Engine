package bimentional.components;

public class FontRenderer extends Component {

  @Override
  public void update(float dt) {
  }

  @Override
  public void start() {
    super.start();
    if (gameObject.getComponent(SpriteRender.class) != null) {
      System.out.println("Starting FontRenderer");
    }
  }
}
