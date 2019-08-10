package uk.co.openkappa.xxhash;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeWrapper {

  private static final Unsafe UNSAFE;
  private static final long BYTE_ARRAY_OFFSET;

  static {
    try {
      Class unsafeClass = Class.forName("sun.misc.Unsafe");
      Field declaredField = unsafeClass.getDeclaredField("theUnsafe");
      declaredField.setAccessible(true);
      UNSAFE = (Unsafe) declaredField.get(null);
      BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }



  public static long getLong(byte[] array, int offset) {
    return UNSAFE.getLong(array, BYTE_ARRAY_OFFSET + offset);
  }

  public static int getInt(byte[] array, int offset) {
    return UNSAFE.getInt(array, BYTE_ARRAY_OFFSET + offset);
  }
}
