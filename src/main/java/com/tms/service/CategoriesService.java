package com.tms.service;

import com.tms.models.Categories;
import com.tms.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }

    public Optional<Categories> getCategoryById(Integer id) {
        return categoriesRepository.findById(id);
    }

    public void updateCategory(Categories category) {
        Optional<Categories> fromDb = getCategoryById(category.getId());
        Categories newCategory = fromDb.get();
        newCategory.setCategoryName(category.getCategoryName());
        categoriesRepository.save(newCategory);
    }

    public Categories createCategory(Categories categories) {
        log.info("Saving new {}", categories);
        return categoriesRepository.save(categories);
    }

    public void deleteCategory(Integer id) {
        log.info("Delete category {}", id);
        categoriesRepository.deleteById(id);
    }
}
