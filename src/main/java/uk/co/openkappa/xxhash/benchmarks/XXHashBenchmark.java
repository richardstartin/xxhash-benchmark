package uk.co.openkappa.xxhash.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 1, jvmArgsPrepend = {"--add-modules=jdk.incubator.vector", "-XX:TypeProfileLevel=111"})
public class XXHashBenchmark {

  @Benchmark
  public long xxhash64(XXHash64State state) {
    return state.hasher.hash(state.data, 0L);
  }

  @Benchmark
  public long xxhash32(XXHash32State state) {
    return state.hasher.hash(state.data, 0);
  }
}
