package bimentional.renderer;

import bimentional.Window;
import bimentional.components.SpriteRender;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch {
  private final int POS_SIZE = 2;
  private final int COLOR_SIZE = 4;
  private final int POS_OFFSET = 0;
  private final int COLOR_OFFSET = POS_OFFSET + (POS_SIZE * Float.BYTES);
  private final int VERTEX_SIZE = 6;
  private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;
  private final int maxBatchSize;
  private final Shader shader;
  private final SpriteRender[] sprites;
  private final float[] vertices;
  private boolean isFull;
  private int numSprites;
  private int vaoId, vboId;

  public RenderBatch(int maxBatchSize) {
    shader = new Shader("/shaders/default.glsl");
    shader.compile();

    this.sprites = new SpriteRender[maxBatchSize];
    this.maxBatchSize = maxBatchSize;

//    4 vertex quads
    vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
    this.numSprites = 0;
    this.isFull = false;
  }

  public void start() {
    vaoId = glGenVertexArrays();
    glBindVertexArray(vaoId);

    vboId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboId);
    glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

    int eboId = glGenBuffers();
    int[] indices = generateIndices();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

    glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
    glEnableVertexAttribArray(1);
  }

  public void addSprite(SpriteRender sprite) {
    if (isFull) {
      return;
    }

    sprites[numSprites] = sprite;

    loadVertexProperties(numSprites);
    numSprites++;

    if (numSprites >= maxBatchSize) {
      isFull = true;
    }
  }

  private void loadVertexProperties(int index) {
    SpriteRender sprite = sprites[index];
    int offset = index * 4 * VERTEX_SIZE;

    Vector4f color = sprite.getColor();
    float xAdd = 1;
    float yAdd = 1;

    for (int i = 0; i < 4; i++) {
      if (i == 1) {
        xAdd = 0;
      } else if (i == 2) {
        yAdd = 0;
      } else if (i == 3) {
        xAdd = 1;
      }

//      load position
      vertices[offset] = sprite.getGameObject().transform.position.x + (xAdd * sprite.getGameObject().transform.scale.x);
      vertices[offset + 1] = sprite.getGameObject().transform.position.y + (yAdd * sprite.getGameObject().transform.scale.y);

//      load color
      vertices[offset + 2] = color.x;
      vertices[offset + 3] = color.y;
      vertices[offset + 4] = color.z;
      vertices[offset + 5] = color.w;

      offset += VERTEX_SIZE;
    }
  }

  public void render() {
    glBindBuffer(GL_ARRAY_BUFFER, vboId);
    glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

    shader.use();
    shader.uploadMat4f("uProjection", Window.getScene().getCamera().getProjectionMatrix());
    shader.uploadMat4f("uView", Window.getScene().getCamera().getViewMatrix());

    glBindVertexArray(vaoId);
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, numSprites * 6, GL_UNSIGNED_INT, 0);

    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);

    shader.detach();

  }

  private int[] generateIndices() {
//    6 indices per quad
    int[] elements = new int[maxBatchSize * 6];
    int offset = 0;
    for (int i = 0; i < maxBatchSize; i++) {
      loadElementIndices(elements, i);
    }

    return elements;
  }

  private void loadElementIndices(int[] elements, int index) {
    int offsetArrayIndex = index * 6;
    int offset = index * 4;

    elements[offsetArrayIndex] = offset + 3;
    elements[offsetArrayIndex + 1] = offset + 2;
    elements[offsetArrayIndex + 2] = offset;

    elements[offsetArrayIndex + 3] = offset;
    elements[offsetArrayIndex + 4] = offset + 2;
    elements[offsetArrayIndex + 5] = offset + 1;
  }

  public boolean isFull() {
    return isFull;
  }
}
