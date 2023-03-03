package bimentional.scene;

import bimentional.Camera;
import bimentional.Window;
import bimentional.renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class LevelEditor extends Scene {
  private final float[] vertexArray = {
      100.5f, 0.5f, 0.0f,
      1.0f, 0.0f, 0.0f, 1.0f, //   Bottom right 0
      0.5f, 100.5f, 0.0f,
      0.0f, 1.0f, 0.0f, 1.0f, //   Top left     1
      100.5f, 100.5f, 0.0f,
      1.0f, 0.0f, 1.0f, 1.0f, // Top right    2
      0.5f, 0.5f, 0.0f,
      1.0f, 1.0f, 0.0f, 1.0f, //     Bottom left  3
  };

  // IMPORTANT: Must be in counter-clockwise order
  private final int[] elementArray = {
      2, 1, 0, // Top right triangle
      0, 1, 3 // bottom left triangle
  };
  private Shader defaultShader;
  private int vaoId, vboId, eboId;

  public LevelEditor() {
    System.out.println("LevelEditor");
    Window.get().r = 1f;
    Window.get().g = 1f;
    Window.get().b = 1f;
    Window.get().a = 1f;
  }

  @Override
  public void update(float dt) {
    camera.position.x -= dt * 50;
    camera.position.y -= dt * 50;

    defaultShader.use();
    defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
    defaultShader.uploadMat4f("uView", camera.getViewMatrix());

    // Bind the VAO that we're using
    glBindVertexArray(vaoId);

    // Enable the vertex attribute pointers
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

    // Unbind everything
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);

    glBindVertexArray(0);

    defaultShader.detach();
  }

  @Override
  public void init() {
    this.camera = new Camera(new Vector2f(0, 0));

    defaultShader = new Shader("/shaders/default.glsl");
    defaultShader.compile();

    // ============================================================
    // Generate VAO, VBO, and EBO buffer objects, and send to GPU
    // ============================================================
    vaoId = glGenVertexArrays();
    glBindVertexArray(vaoId);

    // Create a float buffer of vertices
    FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
    vertexBuffer.put(vertexArray).flip();

    // Create VBO upload the vertex buffer
    vboId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboId);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

    // Create the indices and upload
    IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
    elementBuffer.put(elementArray).flip();

    eboId = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

    // Add the vertex attribute pointers
    int positionsSize = 3;
    int colorSize = 4;
    int floatSizeBytes = 4;
    int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
    glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
    glEnableVertexAttribArray(1);
  }
}
