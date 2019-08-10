package uk.co.openkappa.xxhash;

public class UnsafeXXHash32 extends BaseXXHash32 {

  @Override
  protected int getInt(byte[] array, int offset) {
    return UnsafeWrapper.getInt(array, offset);
  }
}
