package com.ardb.spring_test.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClass {
    private String fileName;
    private String downloadUrl;
    private String fileType;
    private long fileSize;
}
