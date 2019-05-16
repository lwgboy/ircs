package com.github.hasoo.ircs.core.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;

public class UploadService {

  private final Path uploadPath;
  @Value("${rest.server.file.upload-dir}")
  private String uploadDir;

  public UploadService() {
    this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
  }
}
