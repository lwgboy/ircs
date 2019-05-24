package com.github.hasoo.ircs.core.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioFileSequence implements FileSequence {

  private FileChannel fileChannel = null;

  public NioFileSequence(String filename) {
    try {
      this.fileChannel = FileChannel
          .open(Paths.get(filename), StandardOpenOption.WRITE, StandardOpenOption.READ
              , StandardOpenOption.CREATE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized int getSequence() {
    try {
      ByteBuffer byteBuffer = ByteBuffer.allocate(4);
      if (-1 == fileChannel.read(byteBuffer, 0)) {
        byteBuffer.putInt(1);
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        return 0;
      } else {
        byteBuffer.flip();
        int seq = byteBuffer.getInt();
        byteBuffer.flip();
        if (Integer.MAX_VALUE == seq) {
          byteBuffer.putInt(0);
        } else {
          byteBuffer.putInt(seq + 1);
        }
        byteBuffer.flip();
        fileChannel.write(byteBuffer, 0);
        return seq;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
