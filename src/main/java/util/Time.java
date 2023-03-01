package util;

public class Time {
  public static float timeStarted = System.nanoTime();

  public static float getTime() {
    return (System.nanoTime() - timeStarted) * 1E-9f;
  }
}
