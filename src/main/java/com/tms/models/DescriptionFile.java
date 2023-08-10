package com.tms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
