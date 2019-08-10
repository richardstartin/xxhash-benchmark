package uk.co.openkappa.xxhash;

import jdk.incubator.vector.LongVector;
import jdk.incubator.vector.VectorShape;
import jdk.incubator.vector.VectorSpecies;

import static java.lang.reflect.Array.getInt;
import static java.lang.reflect.Array.getLong;
import static jdk.incubator.vector.VectorOperators.LSHL;
import static jdk.incubator.vector.VectorOperators.ROL;
import static uk.co.openkappa.xxhash.Constants.*;

public class VectorXXHash64 implements Hasher64 {

  private static final VectorSpecies<Byte> B256 = VectorSpecies.of(byte.class, VectorShape.S_256_BIT);
  private static final VectorSpecies<Long> L256 = VectorSpecies.of(long.class, VectorShape.S_256_BIT);


  @Override
  public long hash(byte[] input, long seed) {
    long hash;
    long remaining = input.length;
    int offset = 0;

    if (remaining >= 32) {
      var vector = ((LongVector) L256.fromValues(PRIME64_1 + PRIME64_2, PRIME64_2, 0L, -PRIME64_1)).add(seed);
      do {
        vector = vector.add(B256.fromArray(input, offset).reinterpretAsLongs().mul(PRIME64_2))
                .lanewise(ROL, 31)
                .mul(PRIME64_1);
        offset += 32;
        remaining -= 32;
      } while (remaining >= 32);

      long v1 = vector.lane(0);
      long v2 = vector.lane(1);
      long v3 = vector.lane(2);
      long v4 = vector.lane(3);

      hash = Long.rotateLeft(v1, 1)
              + Long.rotateLeft(v2, 7)
              + Long.rotateLeft(v3, 12)
              + Long.rotateLeft(v4, 18);

      v1 *= PRIME64_2;
      v1 = Long.rotateLeft(v1, 31);
      v1 *= PRIME64_1;
      hash ^= v1;
      hash = hash * PRIME64_1 + PRIME64_4;

      v2 *= PRIME64_2;
      v2 = Long.rotateLeft(v2, 31);
      v2 *= PRIME64_1;
      hash ^= v2;
      hash = hash * PRIME64_1 + PRIME64_4;

      v3 *= PRIME64_2;
      v3 = Long.rotateLeft(v3, 31);
      v3 *= PRIME64_1;
      hash ^= v3;
      hash = hash * PRIME64_1 + PRIME64_4;

      v4 *= PRIME64_2;
      v4 = Long.rotateLeft(v4, 31);
      v4 *= PRIME64_1;
      hash ^= v4;
      hash = hash * PRIME64_1 + PRIME64_4;
    } else {
      hash = seed + PRIME64_5;
    }

    hash += input.length;

    while (remaining >= 8) {
      long k1 = getLong(input, offset);
      k1 *= PRIME64_2;
      k1 = Long.rotateLeft(k1, 31);
      k1 *= PRIME64_1;
      hash ^= k1;
      hash = Long.rotateLeft(hash, 27) * PRIME64_1 + PRIME64_4;
      offset += 8;
      remaining -= 8;
    }

    if (remaining >= 4) {
      hash ^= getInt(input, offset) * PRIME64_1;
      hash = Long.rotateLeft(hash, 23) * PRIME64_2 + PRIME64_3;
      offset += 4;
      remaining -= 4;
    }

    while (remaining != 0) {
      hash ^= input[offset] * PRIME64_5;
      hash = Long.rotateLeft(hash, 11) * PRIME64_1;
      --remaining;
      ++offset;
    }

    hash ^= hash >>> 33;
    hash *= PRIME64_2;
    hash ^= hash >>> 29;
    hash *= PRIME64_3;
    hash ^= hash >>> 32;
    return hash;
  }
}
