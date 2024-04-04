package com.ardb.spring_test.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public record ProductCreationRequest(int id, String name, MultipartFile image, List<Integer> categoryIds) {
}
