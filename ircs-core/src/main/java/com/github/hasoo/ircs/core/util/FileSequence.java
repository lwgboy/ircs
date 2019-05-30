package com.github.hasoo.ircs.core.util;

import java.io.IOException;

public abstract class FileSequence {

  public synchronized int getSequence() throws IOException {
    int seq = readInt();
    if (-1 == seq) {
      seq = 0;
    } else {
      if (Integer.MAX_VALUE == seq) {
        seq = -1;
      }
    }
    writeInt(seq + 1);
    return seq;
  }

  protected abstract void close() throws IOException;

  protected abstract void writeInt(int n) throws IOException;

  protected abstract int readInt() throws IOException;
}
