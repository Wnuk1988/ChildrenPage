package com.tms.controller;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import com.tms.service.DescriptionFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class DescriptionFileController {
    public final DescriptionFileService descriptionFileService;
    private final Path ROOT_FILE_PATH = Paths.get("data");

    @PostMapping()
    public ResponseEntity<HttpStatus> createFile(@RequestParam("file") MultipartFile file,
                                                 @RequestBody DescriptionFile descriptionFile) {
        try {
            Path pathToFile = this.ROOT_FILE_PATH.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), pathToFile);
            descriptionFileService.createPathToFile(String.valueOf(pathToFile));
            descriptionFileService.createFile(descriptionFile);
                return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<DescriptionFile>> getFiles() {
        List<DescriptionFile> fileList = descriptionFileService.getFiles();
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @GetMapping("/filename/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Path path = ROOT_FILE_PATH.resolve(filename);
        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @GetMapping("/categories/{categories}")
    public ResponseEntity<List<DescriptionFile>> getFileCategories(@PathVariable Categories categories) {
        List<DescriptionFile> fileList = descriptionFileService.getFileCategories(categories);
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<DescriptionFile>> getFileCategory(@PathVariable Genre genre) {
        List<DescriptionFile> fileList = descriptionFileService.getFileGenre(genre);
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
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

    @DeleteMapping("/{nameFile}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable String nameFile) {
        Path path = ROOT_FILE_PATH.resolve(nameFile);
        File file = new File(path.toString());
        Optional<DescriptionFile> descriptionFile = descriptionFileService.getFileById(Integer.valueOf(nameFile));
        descriptionFileService.deleteFile(nameFile);
        Optional<DescriptionFile> descriptionFileDelete = descriptionFileService.getFileById(Integer.valueOf(nameFile));
        if (file.delete() && descriptionFileDelete.isEmpty() && descriptionFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

