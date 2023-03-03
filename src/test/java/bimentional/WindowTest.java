package bimentional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WindowTest {

  @Test
  void get() {
    Window window = Window.get();
    assertNotNull(window);
  }
}