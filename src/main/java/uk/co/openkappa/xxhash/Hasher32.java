package uk.co.openkappa.xxhash;

@FunctionalInterface
public interface Hasher32 {
  int hash(byte[] data, int seed);
}
