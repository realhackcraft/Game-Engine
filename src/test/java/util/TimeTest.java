package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeTest {
  @Test
  void getTime() {
//    call the method to initialize the timeStarted variable
    System.out.println(Time.getTime());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

//    get the time for real
    float time = Time.getTime();
    System.out.println(time);
    assertTrue(time > 0);
  }
}
