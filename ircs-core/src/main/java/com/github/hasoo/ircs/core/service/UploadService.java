package com.github.hasoo.ircs.core.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

  @Value("${rest.server.file.upload-dir}")
  private String uploadDir;

//  private final Path uploadPath;
//  public UploadService() {
//    this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//  }

  public Path createFullPath(String fullpath) {
    File file = new File(fullpath);
    file.mkdirs();
    return file.toPath();
  }

  public void store(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());

    Path targetLocation = Paths.get(this.uploadDir, filename).toAbsolutePath().normalize();
    System.out.println("targetLocation:" + targetLocation.toString());

    try {
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
