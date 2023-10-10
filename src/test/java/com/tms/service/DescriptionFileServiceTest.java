package com.tms.service;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genres;
import com.tms.repository.DescriptionFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.tms.models.Categories.ANIMALS;
import static com.tms.models.Genres.FABLES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class DescriptionFileServiceTest {
    private final Integer ID_VALUE = 5;
    private final Genres GENRES = FABLES;
    private final Categories CATEGORIES = ANIMALS;
    private final Pageable PAGEABLE = PageRequest.of(1, 50);
    @InjectMocks
    private DescriptionFileService descriptionFileService;
    @Mock
    private DescriptionFileRepository descriptionFileRepository;

    @Test
    public void getDescriptionFiles() {
        DescriptionFile descriptionFile = new DescriptionFile();
        descriptionFile.setId(ID_VALUE);
        List<DescriptionFile> descriptionFileList = new ArrayList<>();
        descriptionFileList.add(descriptionFile);
        Mockito.when(descriptionFileRepository.findAll()).thenReturn(descriptionFileList);
        List<DescriptionFile> result = descriptionFileService.getDescriptionFiles();

        assertThat(result, allOf(notNullValue(), equalTo(descriptionFileList)));
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getPaginationDescriptionFiles() {
        descriptionFileService.getPaginationDescriptionFiles(PAGEABLE);
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).findAll((Pageable) any());
    }

    @Test
    public void getDescriptionFilesCategories() {
        descriptionFileService.getDescriptionFilesCategories(CATEGORIES);
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).findByCategories(any());
    }

    @Test
    public void getDescriptionFilesGenres() {
        descriptionFileService.getDescriptionFilesGenres(GENRES);
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).findByGenres(any());
    }

    @Test
    public void getDescriptionFileById() {
        descriptionFileService.getDescriptionFileById(ID_VALUE);
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void createDescriptionFile() {
        descriptionFileService.createDescriptionFile(new DescriptionFile());
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateDescriptionFile() {
        descriptionFileService.updateDescriptionFile(new DescriptionFile());
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteDescriptionFileId() {
        descriptionFileService.deleteDescriptionFileId(ID_VALUE);
        Mockito.verify(descriptionFileRepository, Mockito.times(1)).deleteById(anyInt());
    }
}
