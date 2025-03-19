package com.practice.hrbank.storage;

import com.practice.hrbank.entity.Metadata;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  Long createFile(Long id, byte[] bytes) throws IOException;
}
