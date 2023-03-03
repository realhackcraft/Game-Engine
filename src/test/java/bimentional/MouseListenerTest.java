package bimentional;

import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

import static org.junit.jupiter.api.Assertions.*;

class MouseListenerTest {
  @Test
  void get() {
    MouseListener listener = MouseListener.get();
    assertNotNull(listener);
  }

  @Test
  void getX() {
    MouseListener.mousePosCallback(0, 1, 2);
    assertEquals(1, MouseListener.getX());
  }

  @Test
  void getY() {
    MouseListener.mousePosCallback(0, 1, 2);
    assertEquals(2, MouseListener.getY());
  }

  @Test
  void getDX() {
    MouseListener.mousePosCallback(0, 1, 2);
    MouseListener.mousePosCallback(0, 3, 4);
    assertEquals(2, MouseListener.getDX());
  }

  @Test
  void getDY() {
    MouseListener.mousePosCallback(0, 1, 2);
    MouseListener.mousePosCallback(0, 3, 4);
    assertEquals(2, MouseListener.getDY());
  }

  @Test
  void getScrollX() {
    MouseListener.scrollCallback(0, 1, 2);
    assertEquals(1, MouseListener.getScrollX());
  }

  @Test
  void getScrollY() {
    MouseListener.scrollCallback(0, 1, 2);
    assertEquals(2, MouseListener.getScrollY());
  }

  @Test
  void isDragging() {
    MouseListener.mouseButtonCallback(0, 1, GLFW.GLFW_PRESS, 0);
    MouseListener.mousePosCallback(0, 1, 2);
    assertTrue(MouseListener.isDragging());

    MouseListener.mouseButtonCallback(0, 1, GLFW.GLFW_RELEASE, 0);
    MouseListener.mousePosCallback(0, 1, 2);
    assertFalse(MouseListener.isDragging());
  }

  @Test
  void mouseButtonDown() {
//    test mouse button 1
    MouseListener.mouseButtonCallback(0, 1, GLFW.GLFW_PRESS, 0);
    assertTrue(MouseListener.mouseButtonDown(1));

    MouseListener.mouseButtonCallback(0, 1, GLFW.GLFW_RELEASE, 0);
    assertFalse(MouseListener.mouseButtonDown(1));

    assertFalse(MouseListener.mouseButtonDown(2));
    assertFalse(MouseListener.mouseButtonDown(3));

//    test mouse button 2
    MouseListener.mouseButtonCallback(0, 2, GLFW.GLFW_PRESS, 0);
    assertTrue(MouseListener.mouseButtonDown(2));

    assertFalse(MouseListener.mouseButtonDown(1));
    assertFalse(MouseListener.mouseButtonDown(3));

//    test mouse button 3
    MouseListener.mouseButtonCallback(0, 3, GLFW.GLFW_PRESS, 0);
    assertTrue(MouseListener.mouseButtonDown(3));

    assertFalse(MouseListener.mouseButtonDown(1));
    assertFalse(MouseListener.mouseButtonDown(2));
  }
}