package bimentional;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
  private static KeyListener instance;
  private final boolean[] keyPressed = new boolean[350];

  private KeyListener() {
  }

  public static KeyListener get() {
    if (instance == null) {
      instance = new KeyListener();
    }

    return instance;
  }

  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    KeyListener listener = get();

    if (action == GLFW_PRESS) {
      listener.keyPressed[key] = true;
    } else if (action == GLFW_RELEASE) {
      listener.keyPressed[key] = false;
    }
  }

  public static boolean isKeyPressed(int key) {
    return get().keyPressed[key];
  }
}
