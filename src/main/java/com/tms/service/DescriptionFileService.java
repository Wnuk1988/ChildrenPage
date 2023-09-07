package com.tms.service;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genres;
import com.tms.repository.DescriptionFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DescriptionFileService {
    private final DescriptionFileRepository descriptionFileRepository;

    public List<DescriptionFile> getDescriptionFiles() {
        return descriptionFileRepository.findAll();
    }

    public Page<DescriptionFile> getPaginationDescriptionFiles(Pageable pageable) {
        return descriptionFileRepository.findAll(pageable);
    }

    public List<DescriptionFile> getDescriptionFilesCategories(Categories categories) {
        return descriptionFileRepository.findByCategories(categories);
    }

    public List<DescriptionFile> getDescriptionFilesGenres(Genres genres) {
        return descriptionFileRepository.findByGenres(genres);
    }

    public Optional<DescriptionFile> getDescriptionFileById(Integer id) {
        return descriptionFileRepository.findById(id);
    }

    public void createDescriptionFile(DescriptionFile descriptionFile) {
        log.info("Saving new {}", descriptionFile);
        descriptionFileRepository.save(descriptionFile);
    }

    public void updateDescriptionFile(DescriptionFile descriptionFile) {
        log.info("Update file {}", descriptionFile);
        descriptionFileRepository.saveAndFlush(descriptionFile);
    }

    public void deleteDescriptionFileId(Integer id){
        log.info("Delete file {}", id);
        descriptionFileRepository.deleteById(id);
    }
}
