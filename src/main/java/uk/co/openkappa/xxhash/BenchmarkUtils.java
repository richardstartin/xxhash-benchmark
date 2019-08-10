package uk.co.openkappa.xxhash;

import java.util.concurrent.ThreadLocalRandom;

public class BenchmarkUtils {

  public static byte[] newByteArray(int size) {
    byte[] array = new byte[size];
    ThreadLocalRandom.current().nextBytes(array);
    return array;
  }
}
