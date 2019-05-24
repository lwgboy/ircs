package com.github.hasoo.ircs.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

public class FileSequenceTest {

  @Test
  public void testNioFileSequence() {
    String filename = "./test.seq";
    try {
      if (Paths.get(filename).toFile().exists()) {
        Files.delete(Paths.get(filename));
      }
      FileSequence fileSequence = new NioFileSequence(filename);
      Assert.assertEquals(0, fileSequence.getSequence());
      Assert.assertEquals(1, fileSequence.getSequence());
      Assert.assertEquals(2, fileSequence.getSequence());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testRandomAccessFileSequence() {
    String filename = "./test.seq";
    try {
      if (Paths.get(filename).toFile().exists()) {
        Files.delete(Paths.get(filename));
      }
      FileSequence fileSequence = new RandomAccessFileSequence(filename);
      Assert.assertEquals(0, fileSequence.getSequence());
      Assert.assertEquals(1, fileSequence.getSequence());
      Assert.assertEquals(2, fileSequence.getSequence());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}