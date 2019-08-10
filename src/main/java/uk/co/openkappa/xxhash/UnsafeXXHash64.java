package uk.co.openkappa.xxhash;

public class UnsafeXXHash64 extends BaseXXHash64 {


  @Override
  protected long getLong(byte[] array, int offset) {
    return UnsafeWrapper.getLong(array, offset);
  }

  @Override
  protected int getInt(byte[] array, int offset) {
    return UnsafeWrapper.getInt(array, offset);
  }
}
