package com.practice.hrbank.storage;

import com.practice.hrbank.entity.Metadata;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  Long put(Long id, byte[] bytes);

  InputStream get(Long id);

  ResponseEntity<?> download(Metadata metadata);
}
