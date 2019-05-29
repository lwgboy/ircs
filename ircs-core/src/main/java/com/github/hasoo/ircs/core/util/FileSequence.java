package com.github.hasoo.ircs.core.util;

import java.io.IOException;

public interface FileSequence {

  public int getSequence() throws IOException;

  public void close() throws IOException;

  void writeInt(int n) throws IOException;

  int readInt() throws IOException;
}
