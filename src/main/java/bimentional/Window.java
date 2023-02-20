package bimentional;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private static Window window = null;
  private final String title;
  int width, height;
  private long glfwWindow;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Bimentional";
  }

  public static Window get() {
    if (window == null) {
      window = new Window();
    }

    return window;
  }

  public void run() {
    System.out.println("hello LWJGL " + Version.getVersion() + "!");

    init();
    loop();
  }

  private void init() {
//    Setup an error callback.
    GLFWErrorCallback.createPrint(System.err).set();

//    Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

//    Configure GLFW
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

//    Create the window
    glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
    if (glfwWindow == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    glfwMakeContextCurrent(glfwWindow);

//    Enable v-sync
    glfwSwapInterval(1);

    glfwShowWindow(glfwWindow);

    GL.createCapabilities();
  }

  private void loop() {
    while (!glfwWindowShouldClose(glfwWindow)) {
      glfwPollEvents();

      glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      glfwSwapBuffers(glfwWindow);
    }
  }
}
