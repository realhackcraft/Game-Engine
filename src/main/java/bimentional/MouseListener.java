package bimentional;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

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

  public static MouseListener get() {
    if (instance == null) {
      instance = new MouseListener();
    }

    return instance;
  }

  public static void mousePosCallback(long window, double xPos, double yPos) {
    MouseListener listener = get();
    listener.lastX = listener.xPos;
    listener.lastY = listener.yPos;
    listener.xPos = xPos;
    listener.yPos = yPos;
    listener.isDragging = listener.mouseButtonPressed[0] || listener.mouseButtonPressed[1] || listener.mouseButtonPressed[2];
  }

  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    MouseListener listener = get();

    if (button > listener.mouseButtonPressed.length) return;


    if (action == GLFW_PRESS) {
      listener.mouseButtonPressed[button] = true;
    } else if (action == GLFW_RELEASE) {
      listener.mouseButtonPressed[button] = false;
      listener.isDragging = false;
    }
  }

  public static void scrollCallback(long window, double xOffset, double yOffset) {
    MouseListener listener = get();
    listener.scrollX = xOffset;
    listener.scrollY = yOffset;
  }

  public static void endFrame() {
    MouseListener listener = get();
    listener.scrollX = 0;
    listener.scrollY = 0;
    listener.lastX = listener.xPos;
    listener.lastY = listener.yPos;
  }

  public static float getX() {
    return (float) get().xPos;
  }

  public static float getY() {
    return (float) get().yPos;
  }

  public static float getDX() {
    return (float) (get().lastX - get().xPos);
  }

  public static float getDY() {
    return (float) (get().lastY - get().yPos);
  }

  public static float getScrollX() {
    return (float) get().scrollX;
  }

  public static float getScrollY() {
    return (float) get().scrollY;
  }

  public static boolean isDragging() {
    return get().isDragging;
  }

  public static boolean mouseButtonDown(int button) {
    if (button > get().mouseButtonPressed.length) return false;
    return get().mouseButtonPressed[button];
  }
}
