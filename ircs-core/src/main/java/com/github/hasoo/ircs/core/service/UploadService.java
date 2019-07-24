package com.github.hasoo.ircs.core.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

  @Value("${rest.server.file.upload-dir}")
  private String uploadDir;

//  private final Path uploadPath;
//  public UploadService() {
//    this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//  }

  public void createFullPath() {
    File file = new File(uploadDir);
    file.mkdirs();
  }

  public String getUploadDir() {
    return uploadDir;
  }
}
