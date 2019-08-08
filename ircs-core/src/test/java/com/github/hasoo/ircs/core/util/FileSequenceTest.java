package com.github.hasoo.ircs.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

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

  @Test
  public void testBenchMark() {
    String filename = "./test.seq";

    try {
      if (Paths.get(filename).toFile().exists()) {
        Files.delete(Paths.get(filename));
      }
      FileSequence fileSequence = new RandomAccessFileSequence(filename);
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      for (int i = 0; i < 100; i++) {
        fileSequence.getSequence();
      }
      System.out.println("seq:" + fileSequence.getSequence());
      stopWatch.stop();
      System.out.println(
          stopWatch.getTotalTimeMillis() / 1000 + "." + stopWatch.getTotalTimeMillis() % 1000
              + " second");
      fileSequence.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      if (Paths.get(filename).toFile().exists()) {
        Files.delete(Paths.get(filename));
      }
      FileSequence fileSequence = new NioFileSequence(filename);
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      for (int i = 0; i < 100; i++) {
        fileSequence.getSequence();
      }
      System.out.println("seq:" + fileSequence.getSequence());
      stopWatch.stop();
      System.out.println(
          stopWatch.getTotalTimeMillis() / 1000 + "." + stopWatch.getTotalTimeMillis() % 1000
              + " second");
      fileSequence.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}