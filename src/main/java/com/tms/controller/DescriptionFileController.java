package com.tms.controller;

import com.tms.exception.DescriptionFileNotFoundException;
import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genres;
import com.tms.models.request.RequestIdAndFilename;
import com.tms.models.request.RequestPagination;
import com.tms.service.DescriptionFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@SecurityRequirement(name = "Bearer Authentication")
public class DescriptionFileController {
    private final DescriptionFileService descriptionFileService;
    private final Path ROOT_FILE_PATH = Paths.get("data");

    @Operation(summary = "We get a description of all files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to get a list of files"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping
    public ResponseEntity<List<DescriptionFile>> getDescriptionFiles() {
        List<DescriptionFile> fileList = descriptionFileService.getDescriptionFiles();
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @Operation(summary = "We get file descriptions by pagination", description = "We receive file descriptions by pagination, we need to pass json RequestPagination to the input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to obtain a list of files by pagination"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping("/pagination")
    public ResponseEntity<Page<DescriptionFile>> getPaginationDescriptionFiles(@Valid @RequestBody RequestPagination requestPagination) {
        Page<DescriptionFile> filePage = descriptionFileService.getPaginationDescriptionFiles(PageRequest.of(requestPagination.getOffset(),
                requestPagination.getLimit(), requestPagination.getSort().getSortValue()));
        if (filePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(filePage, HttpStatus.OK);
        }
    }

    @Operation(summary = "We receive the file", description = "We receive a file, we need to provide the file name as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to get file"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
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

    @Operation(summary = "Getting a file description", description = "We receive a description of the file, we need to provide its id as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to get file"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<DescriptionFile> getDescriptionFile(@PathVariable Integer id) {
        DescriptionFile descriptionFile = descriptionFileService.getDescriptionFileById(id).orElseThrow(DescriptionFileNotFoundException::new);
        return new ResponseEntity<>(descriptionFile, HttpStatus.OK);
    }

    @Operation(summary = "We get a description of files by category", description = "We receive a description of files by category, we need to provide a category as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to receive a file by category"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping("/categories/{categories}")
    public ResponseEntity<List<DescriptionFile>> getDescriptionFilesCategories(@PathVariable Categories categories) {
        List<DescriptionFile> fileList = descriptionFileService.getDescriptionFilesCategories(categories);
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @Operation(summary = "We get a description of files by genre", description = "We get a description of the files by genre, you need to send the genre as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to receive a file by genre"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping("/genres/{genres}")
    public ResponseEntity<List<DescriptionFile>> getDescriptionFilesGenres(@PathVariable Genres genres) {
        List<DescriptionFile> fileList = descriptionFileService.getDescriptionFilesGenres(genres);
        if (fileList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fileList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Adding a file", description = "We are adding a file, the file itself must be submitted as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request completed successfully, a new file was created"),
            @ApiResponse(responseCode = "409", description = "The request could not be completed due to a conflicting resource access"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PostMapping()
    public ResponseEntity<HttpStatus> createFile(@RequestParam("file") MultipartFile file) {
        try {
            Path pathToFile = this.ROOT_FILE_PATH.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), pathToFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Operation(summary = "Adding a file description", description = "We add a file description, we need to pass json DescriptionFile as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request completed successfully, file description created"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PostMapping("/description")
    public ResponseEntity<HttpStatus> createDescriptionFile(@RequestBody DescriptionFile descriptionFile) {
        descriptionFileService.createDescriptionFile(descriptionFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Changing the file description", description = "We are changing the file description, we need to pass json DescriptionFile as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request processed successfully, file modified"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PutMapping
    public ResponseEntity<HttpStatus> updateDescriptionFile(@RequestBody DescriptionFile descriptionFile) {
        descriptionFileService.updateDescriptionFile(descriptionFile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting the entire file", description = "We delete the entire file, we need to provide json RequestIdAndFilename as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request processed successfully, entire file deleted"),
            @ApiResponse(responseCode = "409", description = "The request could not be completed due to a conflicting resource access"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllFile(@RequestBody RequestIdAndFilename requestIdAndFilename) {
        Path path = ROOT_FILE_PATH.resolve(requestIdAndFilename.getFileName());
        File file = new File(path.toString());
        Optional<DescriptionFile> descriptionFile = descriptionFileService.getDescriptionFileById(requestIdAndFilename.getFileId());
        descriptionFileService.deleteDescriptionFileId(requestIdAndFilename.getFileId());
        Optional<DescriptionFile> descriptionFileDelete = descriptionFileService.getDescriptionFileById(requestIdAndFilename.getFileId());
        if (file.delete() && descriptionFileDelete.isEmpty() && descriptionFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

