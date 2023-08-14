package com.tms.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="L_user_file",
            joinColumns=  @JoinColumn(name="description_file_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="user_info_id", referencedColumnName="id") )
    private List<UserInfo> userInfoList = new ArrayList<>();
}
