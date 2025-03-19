package com.practice.hrbank.storage.local;

import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "hrbank.storage.type", havingValue = "local")
public class LogFileStorage {

  private final Path root;

  public LogFileStorage(@Value("${hrbank.storage.local.paths.error-log-path}") String root) throws IOException{
    this.root = Paths.get(root);
    if (Files.notExists(this.root)) {
      Files.createDirectory(this.root);
    }
  }

  @PostConstruct
  public void init() throws IOException {
    Files.createDirectories(root);
  }

  public Path createLogFile(String fileName) throws IOException {
    Path logFilePath = Paths.get(String.valueOf(root), fileName);
    if (Files.exists(logFilePath)) {
      Files.delete(logFilePath);
    }
    return Files.createFile(logFilePath);
  }
  public void writeErrorToFile(Path logFilePath, String errorMessage) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardOpenOption.APPEND)) {
      writer.write("Error: " + errorMessage);
      writer.newLine();
    }
  }
}
