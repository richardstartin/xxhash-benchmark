package uk.co.openkappa.xxhash;

@FunctionalInterface
public interface Hasher64 {
  long hash(byte[] data, long seed);
}
