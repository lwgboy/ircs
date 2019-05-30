package com.github.hasoo.ircs.core.util;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileSequence extends FileSequence {

  private RandomAccessFile randomAccessFile;

  public RandomAccessFileSequence(String filename) throws FileNotFoundException {
    randomAccessFile = new RandomAccessFile(filename, "rw");
  }

  @Override
  public void close() throws IOException {
    randomAccessFile.close();
  }

  @Override
  protected void writeInt(int n) throws IOException {
    randomAccessFile.seek(0);
    randomAccessFile.writeInt(n);
  }

  @Override
  protected int readInt() throws IOException {
    try {
      randomAccessFile.seek(0);
      return randomAccessFile.readInt();
    } catch (EOFException ignored) {
    }
    return -1;
  }
}
