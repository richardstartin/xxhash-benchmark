package uk.co.openkappa.xxhash;

public class NoSWARXXHash32 extends BaseXXHash32 {

  @Override
  protected int getInt(byte[] bytes, int pos) {
    return (bytes[pos] & 0xFF)
            | ((bytes[pos + 1] & 0xFF) << 8)
            | ((bytes[pos + 2] & 0xFF) << 16)
            | ((bytes[pos + 3] & 0xFF) << 24);
  }
}
