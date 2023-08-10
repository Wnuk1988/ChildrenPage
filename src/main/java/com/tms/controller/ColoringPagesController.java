package com.tms.controller;

import com.tms.models.Categories;
import com.tms.models.ColoringPages;
import com.tms.service.ColoringPagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coloringPages")
public class ColoringPagesController {
    private final ColoringPagesService coloringPagesService;

    @GetMapping("/")
    public ResponseEntity<List<ColoringPages>> getColoringPages() {
        List<ColoringPages> coloringPagesList = coloringPagesService.coloringPagesList();
        if (coloringPagesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK); // как вернуть обект
        }
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<ColoringPages>> getColoringPagesByCategories(@RequestBody Categories category) {
        List<ColoringPages> coloringPages = coloringPagesService.getColoringPagesByCategories(category);
        if (coloringPages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK); // как вернуть обект
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColoringPages> getColoringPageById(@PathVariable Integer id) {
        Optional<ColoringPages> coloringPages = coloringPagesService.getColoringPageById(id);
        if (coloringPages.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK); // как вернуть обект
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateColoringPage(@RequestBody ColoringPages coloringPage) {
        coloringPagesService.updateColoringPage(coloringPage);
        Optional<ColoringPages> coloringPageUpdateOptional = coloringPagesService.getColoringPageById(coloringPage.getId());
        ColoringPages coloringPageUpdate = coloringPageUpdateOptional.get();
        if (coloringPage.equals(coloringPageUpdate)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createColoringPage(@RequestBody ColoringPages coloringPage) {
        ColoringPages coloringPageSaved = coloringPagesService.createColoringPage(coloringPage);
        Optional<ColoringPages> coloringPageResult = coloringPagesService.getColoringPageById(coloringPageSaved.getId());
        if (coloringPageResult.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteColoringPage(@PathVariable Integer id) {
        Optional<ColoringPages> coloringPageUpdate = coloringPagesService.getColoringPageById(id);
        coloringPagesService.deleteColoringPage(id);
        Optional<ColoringPages> coloringPage = coloringPagesService.getColoringPageById(id);
        if (coloringPage.isEmpty() && coloringPageUpdate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}


