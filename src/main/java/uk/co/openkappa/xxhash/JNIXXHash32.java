package uk.co.openkappa.xxhash;

import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHashFactory;

public class JNIXXHash32 implements Hasher32 {

  private static final XXHash32 IMPL = XXHashFactory.fastestInstance().hash32();


  @Override
  public int hash(byte[] data, int seed) {
    return IMPL.hash(data, 0, data.length, seed);
  }
}
