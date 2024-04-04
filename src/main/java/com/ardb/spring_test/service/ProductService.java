package com.ardb.spring_test.service;

import java.io.File;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.ardb.spring_test.exception.*;
import com.ardb.spring_test.model.*;
import com.ardb.spring_test.repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        final var isExisted = productRepository.existsById(product.getId());
        if (isExisted) {
            throw new ProductAlreadyHaveException("This Product ID : " + product.getId() + " Already Have");
        }
        final var dbCategories = categoryRepository.findAll();
        final Predicate<Category> checkProdductCategory = c -> dbCategories
                .stream()
                .anyMatch(category -> c.getId() == category.getId());

        final var insertable = product
                .getCategories()
                .stream()
                .allMatch(checkProdductCategory);
        if (!insertable) {
            throw new CategoryNotFoundException("Category not found ");
        }
        final var categories = dbCategories
                .stream()
                .filter(c -> product.getCategories().stream().anyMatch(pc -> pc.getId() == c.getId()))
                .collect(Collectors.toList());
        product.setCategories(categories);
        return productRepository.save(product);
    }

    public void delete(int id) {
        final var isExisted = productRepository.existsById(id);
        if (!isExisted) {
            throw new ProductNotFoundException("Product Not Found ID : " + id);
        }
        String directoryPath = "/pos/files/";

        final var deleteImage = productRepository.findById(id).get().getImage();
        File imageToDelete = new File(directoryPath + deleteImage);
        if (imageToDelete.exists()) {
            imageToDelete.delete();
        }
        productRepository.deleteById(id);
    }

    public Product update(int id, Product product) {
        final var isProExisted = productRepository.existsById(id);
        if (!isProExisted) {
            throw new ProductNotFoundException("Product Not Found ID : " + id);
        }
        final var oldProduct = productRepository.findById(id);
        product.setId(id);
        product.setImage(oldProduct.get().getImage());
        product.setCategories(oldProduct.get().getCategories());
        return productRepository.save(product);
    }
}
