package com.tms.service;

import com.tms.models.ColoringPages;
import com.tms.repository.ColoringPagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColoringPagesService {
    private final ColoringPagesRepository coloringPagesRepository;

    public List<ColoringPages> coloringPagesList() {
        return coloringPagesRepository.findAll();
    }

    public List<ColoringPages> getColoringPagesByCategories(String category) {
        return coloringPagesRepository.findByCategories(category);
    }

    public Optional<ColoringPages> getColoringPageById(Integer id) {
        return coloringPagesRepository.findById(id);
    }

    public void updateColoringPage(ColoringPages coloringPage) {
        Optional<ColoringPages> fromDb = getColoringPageById(coloringPage.getId());
        ColoringPages newColoringPage = fromDb.get();
        newColoringPage.setColoringPageName(coloringPage.getColoringPageName());
        newColoringPage.setCategory(coloringPage.getCategory());
        newColoringPage.setPathToFile(coloringPage.getPathToFile());
        coloringPagesRepository.save(newColoringPage);
    }

    public ColoringPages createColoringPage(ColoringPages coloringPage) {
        log.info("Saving new {}", coloringPage);
        return coloringPagesRepository.save(coloringPage);
    }

    public void deleteColoringPage(Integer id) {
        log.info("Delete coloring page {}", id);
        coloringPagesRepository.deleteById(id);
    }
}
