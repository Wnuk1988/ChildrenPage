package com.tms.controller;

import com.tms.models.Categories;
import com.tms.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping("/")
    public ResponseEntity<List<Categories>> getCategories() {
        List<Categories> categories = categoriesService.getCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK); // как вернуть обект
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Integer id) {
        Optional<Categories> categories = categoriesService.getCategoryById(id);
        if (categories.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK); // как вернуть обект
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateCategory(@RequestBody Categories category) {
        categoriesService.updateCategory(category);
        Optional<Categories> categoryUpdateOptional = categoriesService.getCategoryById(category.getId());
        Categories categoryUpdate = categoryUpdateOptional.get();
        if (category.equals(categoryUpdate)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCategory(@RequestBody Categories category) {
        Categories categorySaved = categoriesService.createCategory(category);
        Optional<Categories> categoryResult = categoriesService.getCategoryById(categorySaved.getId());
        if (categoryResult.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Integer id) {
        Optional<Categories> categoryUpdate = categoriesService.getCategoryById(id);
        categoriesService.deleteCategory(id);
        Optional<Categories> category = categoriesService.getCategoryById(id);
        if (category.isEmpty() && categoryUpdate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
