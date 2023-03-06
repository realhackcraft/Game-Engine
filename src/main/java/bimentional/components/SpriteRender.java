package bimentional.components;

public class SpriteRender extends Component {

  @Override
  public void update(float dt) {
    System.out.println("Updating SpriteRender");
  }

  @Override
  public void start() {
    super.start();
    System.out.println("Starting SpriteRender");
  }
}
