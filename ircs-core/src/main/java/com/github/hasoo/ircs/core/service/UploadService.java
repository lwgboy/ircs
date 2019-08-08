package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.util.FileSequence;
import com.github.hasoo.ircs.core.util.HUtil;
import com.github.hasoo.ircs.core.util.NioFileSequence;
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

  private FileSequence fileSequence;

  public UploadService() {
    this.fileSequence = new NioFileSequence();
  }

  public void store(MultipartFile file, String username) {
//    String filename = StringUtils.cleanPath(file.getOriginalFilename());

    Path targetLocation = Paths.get(this.uploadDir, filename).toAbsolutePath().normalize();
    System.out.println("targetLocation:" + targetLocation.toString());

    try {
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Path createFullPath(String fullpath) {
    File file = new File(fullpath);
    file.mkdirs();
    return file.toPath();
  }

  private Path getFullPath(String username) {
    String yyyymmddhhmm = HUtil.getCurrentDate12();

    String filename = yyyymmddhhmm + "_" +

    return fullpath;
  }
}
