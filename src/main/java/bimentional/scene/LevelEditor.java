package bimentional.scene;

import bimentional.KeyListener;
import bimentional.Window;

import java.awt.event.KeyEvent;

public class LevelEditor extends Scene {

  private float changeSceneTime = 2f;
  private boolean changingScene = false;

  public LevelEditor() {
    System.out.println("LevelEditor");
    Window.get().r = 1f;
    Window.get().g = 1f;
    Window.get().b = 1f;
  }

  @Override
  public void update(float dt) {
    if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
      changingScene = true;
      System.out.println("Changing scene");
    }

    if (changingScene && changeSceneTime > 0) {
      changeSceneTime -= dt;
      Window.get().r -= dt * 5f;
      Window.get().g -= dt * 5f;
      Window.get().b -= dt * 5f;
    } else if (changingScene) {
      Window.changeScene(SceneType.LEVEL);
    }
  }
}
