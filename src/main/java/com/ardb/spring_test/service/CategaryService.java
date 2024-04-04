package com.ardb.spring_test.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.ardb.spring_test.exception.*;
import com.ardb.spring_test.model.Category;
import com.ardb.spring_test.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class CategaryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {

        final var getCategory = categoryRepository.existsById(id);
        if (!getCategory) {
            throw new CategoryNotFoundException("Category Not Found ID : " + id);
        }
        return categoryRepository.findById(id);
    }

    public Category addCategory(Category category) {
        final var isExisted = categoryRepository.existsById(category.getId());
        if (isExisted) {
            throw new CategoryAlreadyHaveException("This Category Already Have" + category.getId());
        }
        return categoryRepository.save(category);
    }

    public void delete(int id) {
        final var isExisted = categoryRepository.existsById(id);
        final var r = categoryRepository.findById(id);
        if (!isExisted) {
            throw new CategoryNotFoundException("Category ID : " + id + " Not Found");
        }
        for (final var product : r.getProducts()) {
            final var categories = product.getCategories();
            for (var iterator = categories.iterator(); iterator.hasNext();) {
                final var category = iterator.next();
                if (category.getId() == id) {
                    iterator.remove();
                }
            }
        }
        categoryRepository.deleteById(id);
    }

    public Category update(int id, Category category) {
        final var isExisted = categoryRepository.existsById(id);
        if (!isExisted) {
            throw new CategoryNotFoundException("Category Not Found ID : " + id);
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

}
