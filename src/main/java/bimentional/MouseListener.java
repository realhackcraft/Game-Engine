package bimentional;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

@SuppressWarnings("unused")
public class MouseListener {
  private static MouseListener instance;
  private final boolean[] mouseButtonPressed = new boolean[3];
  private double scrollX;
  private double scrollY;
  private double xPos;
  private double yPos;
  private double lastX;
  private double lastY;
  private boolean isDragging;


  private MouseListener() {
    this.scrollX = 0;
    this.scrollY = 0;
    this.xPos = 0;
    this.yPos = 0;
    this.lastX = 0;
    this.lastY = 0;
  }

  /**
   * Callback for mouse position updates
   *
   * @param window the window that the mouse is in
   * @param xPos   the x position of the mouse
   * @param yPos   the y position of the mouse
   */
  public static void mousePosCallback(long window, double xPos, double yPos) {
    MouseListener listener = get();
    listener.lastX = listener.xPos;
    listener.lastY = listener.yPos;
    listener.xPos = xPos;
    listener.yPos = yPos;
    listener.isDragging = listener.mouseButtonPressed[0] || listener.mouseButtonPressed[1] || listener.mouseButtonPressed[2];
  }

  /**
   * @return The instance of the MouseListener if it exists, otherwise creates a new instance and returns it
   */
  public static MouseListener get() {
    if (instance == null) {
      instance = new MouseListener();
    }

    return instance;
  }

  /**
   * Callback for mouse button updates
   *
   * @param window the window that the mouse is in
   * @param button the button that was pressed
   * @param action the action that was performed on the button
   * @param mods   the modifier keys that were pressed
   */
  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    MouseListener listener = get();

    if (button > listener.mouseButtonPressed.length) return;

    System.out.println("Button: " + button + " Action: " + action);
    if (action == GLFW_PRESS) {
      listener.mouseButtonPressed[button] = true;
    } else if (action == GLFW_RELEASE) {
      listener.mouseButtonPressed[button] = false;
      listener.isDragging = false;
    }
  }

  /**
   * Callback for mouse scroll updates
   *
   * @param window  the window that the mouse is in
   * @param xOffset the x offset of the scroll
   * @param yOffset the y offset of the scroll
   */
  public static void scrollCallback(long window, double xOffset, double yOffset) {
    MouseListener listener = get();
    listener.scrollX = xOffset;
    listener.scrollY = yOffset;
  }

  /**
   * Called at the end of each frame to reset the mouse position
   */
  public static void endFrame() {
    MouseListener listener = get();
    listener.scrollX = 0;
    listener.scrollY = 0;
    listener.lastX = listener.xPos;
    listener.lastY = listener.yPos;
  }

  /**
   * Getter for the mouse's x position
   *
   * @return The mouse's x position
   */
  public static float getX() {
    return (float) get().xPos;
  }

  /**
   * Getter for the mouse's y position
   *
   * @return The mouse's y position
   */
  public static float getY() {
    return (float) get().yPos;
  }

  /**
   * Getter for the mouse's x movement(dx)
   *
   * @return The mouse's x movement(dx)
   */
  public static float getDX() {
    return (float) (get().lastX - get().xPos);
  }

  /**
   * Getter for the mouse's y movement(dy)
   *
   * @return The mouse's y movement(dy)
   */
  public static float getDY() {
    return (float) (get().lastY - get().yPos);
  }

  /**
   * Getter for the mouse's x scroll
   *
   * @return The mouse's x scroll
   */
  public static float getScrollX() {
    return (float) get().scrollX;
  }

  /**
   * Getter for the mouse's y scroll
   *
   * @return The mouse's y scroll
   */
  public static float getScrollY() {
    return (float) get().scrollY;
  }

  /**
   * Detects whether the mouse is currently dragging or not
   *
   * @return True, if the mouse is dragging, false otherwise
   */
  public static boolean isDragging() {
    return get().isDragging;
  }

  /**
   * Detects whether the mouse button is currently pressed or not
   *
   * @param button The button to check
   * @return True, if the button is pressed, false otherwise
   */
  public static boolean mouseButtonDown(int button) {
    if (button > get().mouseButtonPressed.length) return false;
    return get().mouseButtonPressed[button];
  }
}
