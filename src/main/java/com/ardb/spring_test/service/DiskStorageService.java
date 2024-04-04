package com.ardb.spring_test.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ardb.spring_test.exception.FileNotFoundException;
import com.ardb.spring_test.exception.FileStorageException;

@Service
public class DiskStorageService {
    static final String FOLDER = "/pos/files/";

    public String save(MultipartFile file) throws FileStorageException {
        final var directory = new File(FOLDER);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        final var fileId = UUID.randomUUID();
        var fileName = fileId.toString();
        final var originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            final var nameParts = originalFileName.split(Pattern.quote("."));

            if (nameParts.length > 1) {
                fileName += "." + nameParts[nameParts.length - 1];
            }
        }

        final var savedFile = new File(FOLDER + fileName);
        try (final var os = new FileOutputStream(savedFile)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file " + fileName, e);
        }

        return fileName;
    }

    public File get(String fileName) {
        final var file = new File(FOLDER + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File Not Found");
        }
        return file;
    }
}
