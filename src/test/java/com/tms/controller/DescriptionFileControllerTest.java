package com.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genres;
import com.tms.security.filter.JwtAuthenticationFilter;
import com.tms.service.DescriptionFileService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.tms.models.Categories.ANIMALS;
import static com.tms.models.Genres.FABLES;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DescriptionFileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DescriptionFileControllerTest {
    @MockBean
    DescriptionFileService descriptionFileService;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private ObjectMapper objectMapper;
    static List<DescriptionFile> descriptionFileList = new ArrayList<>();
    static DescriptionFile descriptionFile = new DescriptionFile();
    private final Genres GENRES = FABLES;
    private final Categories CATEGORIES = ANIMALS;

    @BeforeAll
    public static void beforeALL() {
        descriptionFile.setId(5);
        descriptionFileList.add(descriptionFile);
    }

    @Test
    public void getDescriptionFiles() throws Exception {
        Mockito.when(descriptionFileService.getDescriptionFiles()).thenReturn(descriptionFileList);
        mockMvc.perform(get("/file"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(5)));
    }

    @Test
    public void getDescriptionFilesGenres() throws Exception {
        Mockito.when(descriptionFileService.getDescriptionFilesGenres(GENRES)).thenReturn(descriptionFileList);
        mockMvc.perform(get("/file/genres/FABLES"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(5)));
    }

    @Test
    public void getDescriptionFilesCategories() throws Exception {
        Mockito.when(descriptionFileService.getDescriptionFilesCategories(CATEGORIES)).thenReturn(descriptionFileList);
        mockMvc.perform(get("/file/categories/ANIMALS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(5)));
    }

    @Test
    public void createDescriptionFile() throws Exception {
        DescriptionFileService mockUS = Mockito.mock(DescriptionFileService.class);
        Mockito.doNothing().when(mockUS).createDescriptionFile(any());

        mockMvc.perform(post("/file/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(descriptionFile)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateDescriptionFile() throws Exception {
        DescriptionFileService mockUS = Mockito.mock(DescriptionFileService.class);
        Mockito.doNothing().when(mockUS).updateDescriptionFile(any());

        mockMvc.perform(put("/file")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(descriptionFile)))
                .andExpect(status().isNoContent());
    }
}
