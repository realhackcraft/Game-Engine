package bimentional.renderer;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
  private final int textureId;

  public Texture(String filePath) throws IOException {

//    generate texture on GPU
    textureId = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, textureId);

//    set texture parameters
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

//    pixelate texture if stretched
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//    pixelate texture if shrunk
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

//    allow transparency
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    IntBuffer width = BufferUtils.createIntBuffer(1);
    IntBuffer height = BufferUtils.createIntBuffer(1);
    IntBuffer channels = BufferUtils.createIntBuffer(1);
    ByteBuffer image = stbi_load(filePath, width, height, channels, 0);

    if (image != null) {
//      check if image is RGB or RGBA
      if (channels.get(0) == 3) {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
      } else if (channels.get(0) == 4) {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
      } else {
        throw new IOException("Error: image with " + channels.get(0) + " channels are unsupported, only 3 or 4 channels are supported.");
      }
    } else {
      throw new IOException("Error: failed to load a texture file from " + filePath + "!\n Reason: " + stbi_failure_reason());
    }

    stbi_image_free(image);
  }

  public void bind() {
    glBindTexture(GL_TEXTURE_2D, textureId);
  }

  public void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
