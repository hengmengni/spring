package com.ardb.spring_test.controller;

import com.ardb.spring_test.dto.CategoryResponse;
import com.ardb.spring_test.dto.ProductResponse;
import com.ardb.spring_test.model.*;
import com.ardb.spring_test.service.CategaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoriesController {
    private final CategaryService categaryService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CategoryResponse>> categories() {
        final var categories = categaryService
                .getCategories()
                .stream()
                .map(p -> new CategoryResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CategoryResponse> categoriesById(@PathVariable int id) {
        final var getCategoryById = categaryService.getCategoryById(id);
        final var categoryById = new CategoryResponse(getCategoryById.getId(), getCategoryById.getName());

        return new ResponseEntity<>(categoryById, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CategoryResponse> createCategorie(@Valid @RequestBody Category category) {

        final var savedCategory = categaryService.addCategory(category);
        final var response = new CategoryResponse(savedCategory.getId(), savedCategory.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateCategorie(@PathVariable int id, @RequestBody Category category) {
        final var updatedCategory = categaryService.update(id, category);
        final var response = new CategoryResponse(updatedCategory.getId(), updatedCategory.getName());
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> deleteCategorie(@PathVariable int id, Product product) {
        categaryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("Deletion successful"));

    }

    List<ProductResponse> convertProductResponses(Category category) {
        return category
                .getProducts()
                .stream()
                .map(p -> new ProductResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

}
