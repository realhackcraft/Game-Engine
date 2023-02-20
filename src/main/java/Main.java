import bimentional.Window;

public class Main {
  public static void main(String[] args) {
    try {
      Window window = Window.get();
      window.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
