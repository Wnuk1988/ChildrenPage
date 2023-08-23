package com.tms.service;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import com.tms.models.request.RequestIdAndFilename;
import com.tms.repository.DescriptionFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DescriptionFileService {
    private final DescriptionFileRepository descriptionFileRepository;

    public List<DescriptionFile> getFiles() {
        return descriptionFileRepository.findAll();
    }

    public List<DescriptionFile> getFileCategories(Categories categories) {
        return descriptionFileRepository.findByCategories(categories);
    }

    public List<DescriptionFile> getFileGenre(Genre genre) {
        return descriptionFileRepository.findByGenre(genre);
    }

    public Optional<DescriptionFile> getFileById(Integer id) {
        return descriptionFileRepository.findById(id);
    }

    public void createFile(DescriptionFile file) {
        log.info("Saving new {}", file);
        descriptionFileRepository.save(file);
    }

    public void createPathToFile(String pathToFile) {
        log.info("Saving path to file {}", pathToFile);
        descriptionFileRepository.creatPathToFile(pathToFile);
    }

    public void updateFile(DescriptionFile file) {
        log.info("Update file {}", file);
        descriptionFileRepository.saveAndFlush(file);
    }

    public void deleteFileId(Integer id){
        log.info("Delete file {}", id);
        descriptionFileRepository.deleteById(id);
    }
}
