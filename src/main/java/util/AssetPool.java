package util;

import bimentional.components.Spritesheet;
import bimentional.renderer.Shader;
import bimentional.renderer.Texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
  private static final Map<String, Shader> shaders = new HashMap<>();
  private static final Map<String, Texture> textures = new HashMap<>();
  private static final Map<String, Spritesheet> spriteSheets = new HashMap<>();

  public static Shader getShader(String filePath) {
    File file = new File(filePath);
    if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
      return AssetPool.shaders.get(file.getAbsolutePath());
    } else {
      Shader shader = new Shader(filePath);
      shader.compile();
      AssetPool.shaders.put(file.getAbsolutePath(), shader);
      return shader;
    }
  }

  public static Texture getTexture(String filePath) {
    File file = new File(filePath);
    if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
      return AssetPool.textures.get(file.getAbsolutePath());
    } else {
      try {
        Texture texture = new Texture(filePath);
        AssetPool.textures.put(file.getAbsolutePath(), texture);

        return texture;
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
  }

  public static void addSpriteSheet(String resourceName, Spritesheet spriteSheet) {
    File file = new File(resourceName);
    if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())) {
      AssetPool.spriteSheets.put(file.getAbsolutePath(), spriteSheet);
    }
  }

  public static Spritesheet getSpriteSheet(String resourceName) {
    File file = new File(resourceName);
    if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())) {
      System.err.println("Sprite sheet " + resourceName + " was not found in the asset pool");
    }
//    TODO add a missing texture as the default texture
    return AssetPool.spriteSheets.getOrDefault(file.getAbsolutePath(), null);
  }
}
