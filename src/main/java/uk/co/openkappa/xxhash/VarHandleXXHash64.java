package uk.co.openkappa.xxhash;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

public class VarHandleXXHash64 extends BaseXXHash64 {

  private static final VarHandle LONG_HANDLE = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.LITTLE_ENDIAN);
  private static final VarHandle INT_HANDLE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.LITTLE_ENDIAN);

  @Override
  protected long getLong(byte[] array, int offset) {
    return (long)LONG_HANDLE.get(array, offset);
  }

  @Override
  protected int getInt(byte[] array, int offset) {
    return (int)INT_HANDLE.get(array, offset);
  }
}
