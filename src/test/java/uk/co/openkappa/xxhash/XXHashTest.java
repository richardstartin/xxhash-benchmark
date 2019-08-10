package uk.co.openkappa.xxhash;

import net.jpountz.xxhash.XXHashFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XXHashTest {

  @DataProvider
  public static Object[][] xxhash64() {
    return new Object[][] {
            args(new NoSWARXXHash64()),
            args(new UnsafeXXHash64()),
            args(new VarHandleXXHash64()),
            args(new VectorXXHash64())
    };
  }

  @DataProvider
  public static Object[][] xxhash32() {
    return new Object[][] {
            args(new NoSWARXXHash32()),
            args(new UnsafeXXHash32()),
            args(new VarHandleXXHash32()),
            args(new VectorXXHash32())
    };
  }



  @Test(dataProvider = "xxhash64")
  public void testXXHash64AgainstReference(String name, Hasher64 hasher) {
    byte[] data = BenchmarkUtils.newByteArray(2000);
    var reference = XXHashFactory.fastestInstance().hash64();
    long referenceHash = reference.hash(data, 0, data.length, 0L);
    long testedHash = hasher.hash(data, 0L);
    Assert.assertEquals(testedHash, referenceHash);
  }

  @Test(dataProvider = "xxhash32")
  public void testXXHash32AgainstReference(String name, Hasher32 hasher) {
    byte[] data = BenchmarkUtils.newByteArray(16 * 200);
    var reference = XXHashFactory.fastestInstance().hash32();
    int referenceHash = reference.hash(data, 0, data.length, 0);
    int testedHash = hasher.hash(data, 0);
    Assert.assertEquals(testedHash, referenceHash);
  }

  private static Object[] args(Hasher64 hash) {
    return new Object[] {hash.getClass().getName(), hash};
  }

  private static Object[] args(Hasher32 hash) {
    return new Object[] {hash.getClass().getName(), hash};
  }

}