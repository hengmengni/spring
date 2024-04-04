package com.ardb.spring_test.dto;

import java.util.List;

public record CategoryInProduct(int id, String name, String imageUrl, List<CategoryResponse> categories) {

}