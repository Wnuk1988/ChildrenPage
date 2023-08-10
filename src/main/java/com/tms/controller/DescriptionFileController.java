package com.tms.controller;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import com.tms.service.DescriptionFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class DescriptionFileController {
    public final DescriptionFileService descriptionFileService;
    private final Path ROOT_FILE_PATH = Paths.get("data");

    @GetMapping
    public ResponseEntity<List<DescriptionFile>> getFiles() {
        List<DescriptionFile> fileList = descriptionFileService.getFiles();
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DescriptionFile> getFile(@PathVariable Integer id) {
        Optional<DescriptionFile> descriptionFileOptional = descriptionFileService.getFileById(id);
        if (descriptionFileOptional.isPresent()) {
            DescriptionFile descriptionFile = descriptionFileOptional.get();
            return new ResponseEntity<>(descriptionFile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{nameFile}")
    public ResponseEntity<DescriptionFile> getNameFile(@PathVariable String nameFile) {
        Optional<DescriptionFile> descriptionFileOptional = descriptionFileService.findDescriptionFileByNameFile(nameFile);
        if (descriptionFileOptional.isPresent()) {
            DescriptionFile descriptionFile = descriptionFileOptional.get();
            return new ResponseEntity<>(descriptionFile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{categories}")
    public ResponseEntity<List<DescriptionFile>> getFileCategories(@PathVariable Categories categories) {
        List<DescriptionFile> fileList = descriptionFileService.getFileCategories(categories);
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<DescriptionFile>> getFileCategory(@PathVariable Genre genre) {
        List<DescriptionFile> fileList = descriptionFileService.getFileGenre(genre);
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createFile(@RequestBody DescriptionFile file) {
        DescriptionFile descriptionFileSaved = descriptionFileService.createFile(file);
        Optional<DescriptionFile> descriptionFileResult = descriptionFileService.getFileById(descriptionFileSaved.getId());
        if (descriptionFileResult.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateFile(@RequestBody DescriptionFile file) {
        descriptionFileService.updateFile(file);
        Optional<DescriptionFile> descriptionFileUpdatedOptional = descriptionFileService.getFileById(file.getId());
        if (descriptionFileUpdatedOptional.isPresent()) {
            DescriptionFile descriptionFileUpdated = descriptionFileUpdatedOptional.get();
            if (file.equals(descriptionFileUpdated)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable Integer id) {
        Optional<DescriptionFile> descriptionFileUpdated = descriptionFileService.getFileById(id);
        descriptionFileService.deleteFile(id);
        Optional<DescriptionFile> descriptionFile = descriptionFileService.getFileById(id);
        if (descriptionFile.isEmpty() && descriptionFileUpdated.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
