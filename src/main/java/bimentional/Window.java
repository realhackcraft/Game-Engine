package bimentional;

import bimentional.scene.Level;
import bimentional.scene.LevelEditor;
import bimentional.scene.Scene;
import bimentional.scene.SceneType;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private static Scene currentScene;
  private static Window window = null;
  private final String title;
  public float r, g, b, a;
  int width, height;
  private long glfwWindow;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Bimentional";
  }

  public void run() {
    System.out.println("hello LWJGL " + Version.getVersion() + "!");

    init();
    loop();

    glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    glfwTerminate();
    Objects.requireNonNull(glfwSetErrorCallback(null)).free();
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

    glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::scrollCallback);
    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

    glfwMakeContextCurrent(glfwWindow);

//    Enable v-sync
    glfwSwapInterval(1);

    glfwShowWindow(glfwWindow);

    GL.createCapabilities();

    Window.changeScene(SceneType.LEVEL_EDITOR);
  }

  private void loop() {
    float beginTime = Time.getTime();
    float endTime;
    float dt = -1f;

    while (!glfwWindowShouldClose(glfwWindow)) {
      glfwPollEvents();

      glClearColor(r, g, b, a);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      if (dt >= 0) {
        currentScene.update(dt);
      }

      glfwSwapBuffers(glfwWindow);

      updateFPS(1f / dt);
      System.out.println("FPS: " + 1f / dt);

      endTime = Time.getTime();
      dt = endTime - beginTime;
      beginTime = endTime;
    }
  }

  public static void changeScene(SceneType newScene) {
    switch (newScene) {
      case LEVEL_EDITOR:
        currentScene = new LevelEditor();
//        currentScene.init();
        break;
      case LEVEL:
        currentScene = new Level();
        break;
      default:
        assert false : "Invalid scene: " + newScene;
        break;
    }
  }

  private static void updateFPS(float fps) {
    glfwSetWindowTitle(Window.get().glfwWindow, Window.get().title + " | FPS: " + fps);
  }

  public static Window get() {
    if (window == null) {
      window = new Window();
    }

    return window;
  }
}
