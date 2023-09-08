package com.tms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Описание файла")
@Data
@EqualsAndHashCode(exclude = {"userInfoList"})
@ToString(exclude = {"userInfoList"})
@Entity
@Table(name = "description_file")
public class DescriptionFile {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "description_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;
    @Column(name = "name_file")
    private String nameFile;
    @Column(name = "description_file", columnDefinition = "text")
    private String descriptionFile;
    @Column(name = "author")
    private String author;
    @Column(name = "path_to_file")
    private String pathToFile;
    @Column(name = "categories")
    @Enumerated(EnumType.STRING)
    private Categories categories;
    @Column(name = "genres")
    @Enumerated(EnumType.STRING)
    private Genres genres;
    @JsonBackReference
    @ManyToMany(mappedBy = "favoritesFile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserInfo> userInfoList = new ArrayList<>();
}
