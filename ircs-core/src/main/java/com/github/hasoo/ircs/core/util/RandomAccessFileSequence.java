package com.github.hasoo.ircs.core.util;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileSequence implements FileSequence {

  private RandomAccessFile randomAccessFile;

  public RandomAccessFileSequence(String filename) throws FileNotFoundException {
    randomAccessFile = new RandomAccessFile(filename, "rw");
  }

  @Override
  public void close() throws IOException {
    randomAccessFile.close();
  }

  @Override
  public void writeInt(int n) throws IOException {
    randomAccessFile.seek(0);
    randomAccessFile.writeInt(n);
  }

  @Override
  public int readInt() throws IOException {
    try {
      randomAccessFile.seek(0);
      return randomAccessFile.readInt();
    } catch (EOFException ignored) {
    }
    return -1;
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
