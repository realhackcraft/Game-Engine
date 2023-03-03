package bimentional;

import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

class KeyListenerTest {
  @Test
  void keyCallback() {
    KeyListener.keyCallback(0, GLFW.GLFW_KEY_A, 0, GLFW.GLFW_PRESS, 0);
    assert KeyListener.isKeyPressed(GLFW.GLFW_KEY_A);
    KeyListener.keyCallback(0, GLFW.GLFW_KEY_A, 0, GLFW.GLFW_RELEASE, 0);
  }

  @Test
  void get() {
    KeyListener listener = KeyListener.get();
    assert listener != null;
  }

  @Test
  void isKeyPressed() {
    KeyListener.keyCallback(0, GLFW.GLFW_KEY_B, 0, GLFW.GLFW_PRESS, 0);
    assert KeyListener.isKeyPressed(GLFW.GLFW_KEY_B);
    KeyListener.keyCallback(0, GLFW.GLFW_KEY_B, 0, GLFW.GLFW_RELEASE, 0);
  }
}