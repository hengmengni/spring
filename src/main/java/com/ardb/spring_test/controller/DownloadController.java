package com.ardb.spring_test.controller;

import org.springframework.web.bind.annotation.*;
import com.ardb.spring_test.service.DiskStorageService;
import lombok.RequiredArgsConstructor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/download")
public class DownloadController {
    private final DiskStorageService diskStorageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<InputStreamResource> getMethodName(@PathVariable String fileName) throws IOException {
        final var file = diskStorageService.get(fileName);
        final var path = file.toPath();
        var mimeType = Files.probeContentType(path);
        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        final var contentType = MediaType.parseMediaType(mimeType);
        final var stream = new FileInputStream(file);
        // final var headers = new HttpHeaders();
        // headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
        // fileName);
        return ResponseEntity.ok()
                .contentType(contentType)
                // .headers(headers)
                .body(new InputStreamResource(stream));
    }
}
