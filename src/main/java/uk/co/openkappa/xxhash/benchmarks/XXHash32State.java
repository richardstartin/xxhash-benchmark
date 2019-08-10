package uk.co.openkappa.xxhash.benchmarks;

import org.openjdk.jmh.annotations.*;
import uk.co.openkappa.xxhash.*;

@State(Scope.Benchmark)
public class XXHash32State {

  public enum Impl {
    NO_SWAR {
      @Override
      Hasher32 create() {
        return new NoSWARXXHash32();
      }
    },
    UNSAFE {
      @Override
      Hasher32 create() {
        return new UnsafeXXHash32();
      }
    },
    VAR_HANDLE {
      @Override
      Hasher32 create() {
        return new VarHandleXXHash32();
      }
    },
    JNI {
      @Override
      Hasher32 create() {
        return new JNIXXHash32();
      }
    },
    VECTOR {
      @Override
      Hasher32 create() {
        return new VectorXXHash32();
      }
    };
    abstract Hasher32 create();
  }

  @Param({"10", "100", "500", "1000", "2000"})
  int size;

  @Param({"VECTOR", "NO_SWAR", "UNSAFE", "VAR_HANDLE", "JNI"})
  Impl impl;

  byte[] data;

  Hasher32 hasher;


  @Setup(Level.Trial)
  public void init() {
    data = BenchmarkUtils.newByteArray(size);
    hasher = impl.create();
  }
}
