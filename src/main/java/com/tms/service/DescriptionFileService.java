package com.tms.service;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
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
    private final DescriptionFileRepository descriptionFileRepositoryRepository;

    public List<DescriptionFile> getFiles() {
        return descriptionFileRepositoryRepository.findAll();
    }

    public Optional<DescriptionFile> findDescriptionFileByNameFile(String nameFile) {
        return Optional.ofNullable(descriptionFileRepositoryRepository.findDescriptionFileByNameFile(nameFile));
    }

    public List<DescriptionFile> getFileCategories(Categories categories) {
        return descriptionFileRepositoryRepository.findByCategories(categories);
    }

    public List<DescriptionFile> getFileGenre(Genre genre) {
        return descriptionFileRepositoryRepository.findByGenre(genre);
    }

    public Optional<DescriptionFile> getFileById(Integer id) {
        return descriptionFileRepositoryRepository.findById(id);
    }

    public DescriptionFile createFile(DescriptionFile file) {
        log.info("Saving new {}", file);
        return descriptionFileRepositoryRepository.save(file);
    }

    public void updateFile(DescriptionFile file) {
        Optional<DescriptionFile> fromDb = getFileById(file.getId());
        if (fromDb.isPresent()) {
            DescriptionFile newFile = fromDb.get();
            newFile.setNameFile(file.getNameFile());
            newFile.setDescriptionFile(file.getDescriptionFile());
            newFile.setAuthor(file.getAuthor());
            newFile.setPathToFile(file.getPathToFile());
            newFile.setCategories(file.getCategories());
            newFile.setGenre(file.getGenre());
            log.info("Update file {}", file);
            descriptionFileRepositoryRepository.save(newFile);
        } else {
            fromDb.isEmpty();
        }
    }

    public void deleteFile(Integer id) {
        log.info("Delete file {}", id);
        descriptionFileRepositoryRepository.deleteById(id);
    }
}
