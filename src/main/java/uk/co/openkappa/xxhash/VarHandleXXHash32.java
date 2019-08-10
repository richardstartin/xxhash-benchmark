package uk.co.openkappa.xxhash;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

public class VarHandleXXHash32 extends BaseXXHash32 {

  private static final VarHandle INT_HANDLE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.LITTLE_ENDIAN);

  @Override
  protected int getInt(byte[] array, int offset) {
    return (int)INT_HANDLE.get(array, offset);
  }
}
