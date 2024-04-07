package me.giverplay.autochat.utils;

public class ThreadUtils {
  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void delayed(Runnable callback, long millis) {
    new Thread(() -> {
      ThreadUtils.sleep(millis);
      callback.run();
    }).start();
  }

  public static void execute(Runnable runnable) {
    new Thread(runnable).start();
  }
}
