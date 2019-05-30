package com.github.hasoo.ircs.core.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioFileSequence extends FileSequence {

  private FileChannel fileChannel = null;

  private ByteBuffer byteBuffer = ByteBuffer.allocate(4);

  public NioFileSequence(String filename) throws IOException {
    this.fileChannel = FileChannel
        .open(Paths.get(filename), StandardOpenOption.WRITE, StandardOpenOption.READ
            , StandardOpenOption.CREATE);
  }

  @Override
  public void close() throws IOException {
    fileChannel.close();
  }

  @Override
  protected void writeInt(int n) throws IOException {
    byteBuffer.clear();
    byteBuffer.putInt(n);
    byteBuffer.flip();
    fileChannel.write(byteBuffer, 0);
  }

  @Override
  protected int readInt() throws IOException {
    byteBuffer.clear();
    if (-1 == fileChannel.read(byteBuffer, 0)) {
      return -1;
    }
    byteBuffer.flip();
    return byteBuffer.getInt();
  }
}
