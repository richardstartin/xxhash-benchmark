package uk.co.openkappa.xxhash;

public class NoSWARXXHash64 extends BaseXXHash64 {

  @Override
  protected long getLong(byte[] array, int offset) {
    return (array[offset] & 0xFFL)
            | (array[offset + 1] & 0xFFL) << 8
            | (array[offset + 2] & 0xFFL) << 16
            | (array[offset + 3] & 0xFFL) << 24
            | (array[offset + 4] & 0xFFL) << 32
            | (array[offset + 5] & 0xFFL) << 40
            | (array[offset + 6] & 0xFFL) << 48
            | (array[offset + 7] & 0xFFL) << 56;
  }

  @Override
  protected int getInt(byte[] bytes, int pos) {
    return (bytes[pos] & 0xFF)
            | ((bytes[pos + 1] & 0xFF) << 8)
            | ((bytes[pos + 2] & 0xFF) << 16)
            | ((bytes[pos + 3] & 0xFF) << 24);
  }
}
