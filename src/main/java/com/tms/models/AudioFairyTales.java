package com.tms.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audio_fairy_tales")
public class AudioFairyTales {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "AudioFairyTales_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;
    @Column(name = "audio_name")
    private String audioName;
    @Column(name = "author")
    private String author;
    @Column(name = "path_to_file")
    private String pathToFile;
/*        @Column(name = "categories_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categories")
    private Categories category;*/
}
