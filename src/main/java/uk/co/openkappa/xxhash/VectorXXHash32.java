package uk.co.openkappa.xxhash;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorShape;
import jdk.incubator.vector.VectorSpecies;

import static jdk.incubator.vector.VectorOperators.*;
import static uk.co.openkappa.xxhash.Constants.*;
import static uk.co.openkappa.xxhash.UnsafeWrapper.getInt;

public class VectorXXHash32 implements Hasher32 {

  private static final VectorSpecies<Integer> I128 = VectorSpecies.of(int.class, VectorShape.S_128_BIT);
  private static final VectorSpecies<Byte> B128 = VectorSpecies.of(byte.class, VectorShape.S_128_BIT);

  private static final int[] SEEDS = {PRIME1 + PRIME2, PRIME2, 0, -PRIME1};

  @Override
  public int hash(byte[] data, int seed) {
    int end = data.length;
    int offset = 0;
    int h32;
    if (data.length >= 16) {
      int limit = end - 16;
      var vector = ((IntVector) I128.fromArray(SEEDS, 0)).add(seed);
      do {
        vector = vector.add(B128.fromArray(data, offset).reinterpretAsInts().mul(PRIME2))
                .lanewise(ROL, 13)
                .mul(PRIME1);
        offset += 16;
      } while(offset <= limit);

      h32 = Integer.rotateLeft(vector.lane(0), 1)
              + Integer.rotateLeft(vector.lane(1), 7)
              + Integer.rotateLeft(vector.lane(2), 12)
              + Integer.rotateLeft(vector.lane(3), 18);
    } else {
      h32 = seed + PRIME5;
    }

    for(h32 += data.length; offset <= end - 4; offset += 4) {
      h32 += getInt(data, offset) * PRIME3;
      h32 = Integer.rotateLeft(h32, 17) * PRIME4;
    }

    while(offset < end) {
      h32 += (data[offset] & 255) * PRIME5;
      h32 = Integer.rotateLeft(h32, 11) * PRIME1;
      ++offset;
    }

    h32 ^= h32 >>> 15;
    h32 *= PRIME2;
    h32 ^= h32 >>> 13;
    h32 *= PRIME3;
    h32 ^= h32 >>> 16;
    return h32;
  }
}
