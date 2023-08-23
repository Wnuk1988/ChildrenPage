package com.tms.controller;

import com.tms.exception.DescriptionFileNotFoundException;
import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import com.tms.models.request.RequestIdAndFilename;
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
        DescriptionFile descriptionFile = descriptionFileService.getFileById(id).orElseThrow(DescriptionFileNotFoundException::new);
        return new ResponseEntity<>(descriptionFile, HttpStatus.OK);
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

    //TODO: как добавить файл и описание в тело запроса в POSTMAN?
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

    @PutMapping
    public ResponseEntity<HttpStatus> updateFile(@RequestBody DescriptionFile file) {
        descriptionFileService.updateFile(file);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteFile(@RequestBody RequestIdAndFilename requestIdAndFilename) {
        Path path = ROOT_FILE_PATH.resolve(requestIdAndFilename.getFileName());
        File file = new File(path.toString());
        Optional<DescriptionFile> descriptionFile = descriptionFileService.getFileById(requestIdAndFilename.getFileId());
        descriptionFileService.deleteFileId(requestIdAndFilename.getFileId());
        Optional<DescriptionFile> descriptionFileDelete = descriptionFileService.getFileById(requestIdAndFilename.getFileId());
        if (file.delete() && descriptionFileDelete.isEmpty() && descriptionFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

