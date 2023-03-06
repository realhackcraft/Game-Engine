package bimentional.renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
  private final String filePath;
  private boolean beingUsed = false;
  private int shaderProgramId;
  private String vertexShaderSource;
  private String fragmentShaderSource;

  /**
   * Creates a new shader from the specified file path.
   *
   * @param filePath The path to the shader file.
   */
  public Shader(String filePath) {
    this.filePath = filePath;

    try {
      InputStream inputStream = getClass().getResourceAsStream(filePath);

      assert inputStream != null;

      String source = new BufferedReader(new InputStreamReader(inputStream))
          .lines()
          .collect(Collectors.joining("\n"));

      String[] splitSource = source.split("#type( )+([a-zA-Z]+)");

      int index, eol;

      index = source.indexOf("#type") + 6;
      eol = source.indexOf("\n", index);
      String firstPattern = source.substring(index, eol).trim();

      index = source.indexOf("#type", eol) + 6;
      eol = source.indexOf("\n", index);
      String secondPattern = source.substring(index, eol).trim();

      if (firstPattern.equals("vertex")) {
        vertexShaderSource = splitSource[1];
      } else if (firstPattern.equals("fragment")) {
        fragmentShaderSource = splitSource[1];
      } else {
        throw new IOException("Error: Invalid shader type specified: " + firstPattern);
      }

      if (secondPattern.equals("vertex")) {
        vertexShaderSource = splitSource[2];
      } else if (secondPattern.equals("fragment")) {
        fragmentShaderSource = splitSource[2];
      } else {
        throw new IOException("Error: Invalid shader type specified: " + secondPattern);
      }
    } catch (IOException e) {
      e.printStackTrace();
      assert false : "Error: Could not open file for shader: " + filePath;
    }
  }

  /**
   * Compile and link the shader program.
   */
  public void compile() {
    int vertexID, fragmentID;

    vertexID = glCreateShader(GL_VERTEX_SHADER);

    glShaderSource(vertexID, vertexShaderSource);
    glCompileShader(vertexID);

    fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

    glShaderSource(fragmentID, fragmentShaderSource);
    glCompileShader(fragmentID);

    shaderProgramId = glCreateProgram();
    glAttachShader(shaderProgramId, vertexID);
    glAttachShader(shaderProgramId, fragmentID);
    glLinkProgram(shaderProgramId);

    checkCompilationAndLinkingErrors(vertexID, fragmentID);
  }

  private void checkCompilationAndLinkingErrors(int vertexID, int fragmentID) {
    int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
    if (success == GL_FALSE) {
      int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
      throw new RuntimeException("ERROR: Failed to compile vertex shader from " + filePath + '.' + glGetShaderInfoLog(
          vertexID,
          len));
    }

    success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
    if (success == GL_FALSE) {
      int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
      throw new RuntimeException("ERROR: Failed to compile fragment shader from " + filePath + '.' + glGetShaderInfoLog(
          fragmentID,
          len));
    }

    success = glGetProgrami(shaderProgramId, GL_LINK_STATUS);
    if (success == GL_FALSE) {
      int len = glGetProgrami(shaderProgramId, GL_INFO_LOG_LENGTH);
      throw new RuntimeException("ERROR: Failed to link program from " + filePath + '.' + glGetProgramInfoLog(
          shaderProgramId,
          len));
    }
  }

  /**
   * Unbind shader program from the current OpenGL context.
   */
  public void detach() {
    if (beingUsed) {
      glUseProgram(0);
      beingUsed = false;
    }
  }

  public void uploadMat4f(String varName, Matrix4f mat4) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
    mat4.get(matBuffer);
    glUniformMatrix4fv(varLocation, false, matBuffer);
  }

  /**
   * Bind shader program to the current OpenGL context.
   */
  public void use() {
    if (!beingUsed) {
      glUseProgram(shaderProgramId);
      beingUsed = true;
    }
  }

  public void uploadMat3f(String varName, Matrix3f mat3) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
    mat3.get(matBuffer);
    glUniformMatrix3fv(varLocation, false, matBuffer);
  }

  public void uploadVec4f(String varName, Vector4f vec) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
  }

  public void uploadVec3f(String varName, Vector3f vec) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    glUniform3f(varLocation, vec.x, vec.y, vec.z);
  }

  public void uploadVec2f(String varName, Vector2f vec) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    glUniform2f(varLocation, vec.x, vec.y);
  }

  public void uploadFloat(String varName, float val) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    glUniform1f(varLocation, val);
  }

  public void uploadInt(String varName, int val) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    glUniform1i(varLocation, val);
  }

  public void uploadTexture(String varName, int slot) {
    int varLocation = glGetUniformLocation(shaderProgramId, varName);
    use();
    glUniform1i(varLocation, slot);
  }
}
