package com.github.hasoo.ircs.core.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSequence {

  private FileChannel fileChannel = null;

  public FileSequence(String filename) {
    try {
      this.fileChannel = FileChannel
          .open(Paths.get(filename), StandardOpenOption.WRITE, StandardOpenOption.READ
              , StandardOpenOption.CREATE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public synchronized int getSequence() throws IOException {
    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
    int ret = fileChannel.read(byteBuffer, 0);
    if (-1 == ret) {
      byteBuffer.putInt(0);
      fileChannel.write(byteBuffer, 0);
      return 0;
    }
    return byteBuffer.getInt();
  }
}
