package com.github.hasoo.ircs.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileSequence implements FileSequence {

  private RandomAccessFile randomAccessFile;

  public RandomAccessFileSequence(String filename) {
    try {
      randomAccessFile = new RandomAccessFile(filename, "rw");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getSequence() {
    try {
      int seq = randomAccessFile.readInt();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
