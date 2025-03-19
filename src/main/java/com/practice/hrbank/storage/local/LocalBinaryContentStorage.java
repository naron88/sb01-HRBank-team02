package com.practice.hrbank.storage.local;

import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.storage.BinaryContentStorage;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "hrbank.storage.type", havingValue = "local")
public class LocalBinaryContentStorage implements BinaryContentStorage {

  private final Path root;

  public LocalBinaryContentStorage(
      @Value("${hrbank.storage.local.paths.binary-content-path}") String root) {
    this.root = Paths.get(root);
  }

  @PostConstruct
  public void init() throws IOException {
    Files.createDirectories(root);
  }

  @Override
  public Long put(Long id, byte[] bytes) {
    Path filePath = resolvePath(id);
    try (OutputStream outputStream = Files.newOutputStream(filePath)) {
      outputStream.write(bytes);
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file", e);
    }
    return id;
  }

  @Override
  public InputStream get(Long id) {
    Path filePath = resolvePath(id);
    try {
      return Files.newInputStream(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file", e);
    }
  }

  @Override
  public ResponseEntity<Resource> download(Metadata metadata) {
    Path filePath = resolvePath(metadata.getId());
    org.springframework.core.io.Resource resource = new FileSystemResource(filePath);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + metadata.getName() + "\"")
        .header(HttpHeaders.CONTENT_TYPE, metadata.getContentType())
        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(metadata.getSize()))
        .body(resource);
  }

  private Path resolvePath(Long id) {
    return root.resolve(id.toString());
  }
}
