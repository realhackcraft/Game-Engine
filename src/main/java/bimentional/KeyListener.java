package bimentional;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

@SuppressWarnings("unused")
public class KeyListener {
  /**
   * The KeyListener instance
   */
  private static KeyListener instance;
  /**
   * An array of booleans that stores whether each key is pressed or not
   */
  private final boolean[] keyPressed = new boolean[350];

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private KeyListener() {
  }

  /**
   * This method is called by GLFW when a key is pressed or released.
   *
   * @param window   the Window object that received the event
   * @param key      the key that was pressed or released
   * @param scancode the system-specific scancode of the key
   * @param action   the key action. One of: GLFW_PRESS, GLFW_RELEASE or GLFW_REPEAT
   * @param mods     bit field describing which modifier keys were held down
   */
  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    KeyListener listener = get();

    if (action == GLFW_PRESS) {
      listener.keyPressed[key] = true;
    } else if (action == GLFW_RELEASE) {
      listener.keyPressed[key] = false;
    }
  }

  /**
   * @return the KeyListener instance if it exists, otherwise a new instance
   */
  public static KeyListener get() {
    if (instance == null) {
      instance = new KeyListener();
    }

    return instance;
  }

  /**
   * @param key the key to check
   * @return true if the key is pressed, false otherwise
   */
  public static boolean isKeyPressed(int key) {
    return get().keyPressed[key];
  }
}
