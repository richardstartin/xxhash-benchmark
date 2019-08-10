package uk.co.openkappa.xxhash;

import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;

public class JNIXXHash64 implements Hasher64 {

  private static final XXHash64 IMPL = XXHashFactory.fastestInstance().hash64();

  @Override
  public long hash(byte[] input, long seed) {
    return IMPL.hash(input, 0, input.length, seed);
  }
}
