package uk.co.openkappa.xxhash.benchmarks;

import org.openjdk.jmh.annotations.*;
import uk.co.openkappa.xxhash.*;

@State(Scope.Benchmark)
public class XXHash64State {

  public enum Impl {
    NO_SWAR {
      @Override
      Hasher64 create() {
        return new NoSWARXXHash64();
      }
    },
    UNSAFE {
      @Override
      Hasher64 create() {
        return new UnsafeXXHash64();
      }
    },
    VAR_HANDLE {
      @Override
      Hasher64 create() {
        return new VarHandleXXHash64();
      }
    },
    JNI {
      @Override
      Hasher64 create() {
        return new JNIXXHash64();
      }
    },
    VECTOR {
      @Override
      Hasher64 create() {
        return new VectorXXHash64();
      }
    };
    abstract Hasher64 create();
  }

  @Param({"10", "100", "500", "1000", "2000"})
  int size;

  @Param({"NO_SWAR", "UNSAFE", "VAR_HANDLE", "JNI"})
  Impl impl;

  byte[] data;

  Hasher64 hasher;


  @Setup(Level.Trial)
  public void init() {
    data = BenchmarkUtils.newByteArray(size);
    hasher = impl.create();
  }
}
