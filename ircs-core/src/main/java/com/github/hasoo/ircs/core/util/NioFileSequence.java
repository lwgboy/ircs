package com.github.hasoo.ircs.core.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioFileSequence implements FileSequence {

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
  public void writeInt(int n) throws IOException {
    byteBuffer.clear();
    byteBuffer.putInt(n);
    byteBuffer.flip();
    fileChannel.write(byteBuffer, 0);
  }

  @Override
  public int readInt() throws IOException {
    byteBuffer.clear();
    if (-1 == fileChannel.read(byteBuffer, 0)) {
      return -1;
    }
    byteBuffer.flip();
    return byteBuffer.getInt();
  }

  @Override
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
}
