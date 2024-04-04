package com.ardb.spring_test.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.ardb.spring_test.dto.*;
import com.ardb.spring_test.model.*;
import com.ardb.spring_test.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

        private final ProductService productService;
        private final DiskStorageService diskStorageService;

        @GetMapping
        @PreAuthorize("hasAuthority('ROLE_USER')")
        public ResponseEntity<List<CategoryInProduct>> getProduct() {
                final var products = productService
                                .getProducts()
                                .stream()
                                .map(p -> new CategoryInProduct(p.getId(), p.getName(), getImageUrl(p.getImage()),
                                                convertCategoryResponses(p)))
                                .collect(Collectors.toList());
                return new ResponseEntity<>(products, HttpStatus.OK);
        }

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public ResponseEntity<CategoryInProduct> createProduct(@Valid @ModelAttribute ProductCreationRequest product) {
                final var fileName = diskStorageService.save(product.image());
                final var categories = product.categoryIds()
                                .stream()
                                .map(id -> Category
                                                .builder()
                                                .id(id)
                                                .build())
                                .collect(Collectors.toList());
                final var savedProduct = Product
                                .builder()
                                .id(product.id())
                                .name(product.name())
                                .categories(categories)
                                .image(fileName)
                                .build();

                final var createdProduct = productService.addProduct(savedProduct);
                final var imageURL = getImageUrl(createdProduct.getImage());
                final var responseProduct = new CategoryInProduct(createdProduct.getId(), createdProduct.getName(),
                                imageURL,
                                convertCategoryResponses(createdProduct));
                return new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public ResponseEntity<Object> updateProduct(@PathVariable int id, @RequestBody Product product) {
                final var isUpdated = productService.update(id, product);
                final var response = new ProductResponse(isUpdated.getId(), isUpdated.getName());
                return ResponseEntity.ok().body(response);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable("id") int id) {
                productService.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("Deletion successful"));

        }

        List<CategoryResponse> convertCategoryResponses(Product product) {
                return product
                                .getCategories()
                                .stream()
                                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                                .collect(Collectors.toList());
        }

        String getImageUrl(String fileName) {
                if (fileName == null) {
                        // Handle the case where fileName is null
                        return "";
                }
                return ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/download/")
                                .path(fileName)
                                .toUriString();
        }
}
