package com.github.hasoo.ircs.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(classes = {UploadService.class})
public class UploadServiceTest {

  @Autowired
  private UploadService uploadService;

  @Test
  public void testPath() {
    System.out.println(uploadService.getUploadDir());
  }

  @Test
  public void testCreatePath() {
    uploadService.createFullPath();
  }
}