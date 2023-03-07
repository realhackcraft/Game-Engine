package util;

import bimentional.renderer.Shader;
import bimentional.renderer.Texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
  private static final Map<String, Shader> shaders = new HashMap<>();
  private static Map<String, Texture> textures;

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
}
