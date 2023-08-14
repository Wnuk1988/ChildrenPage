package com.tms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CascadeType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorites")
public class Favorites {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "favorites_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;
    @Column(name = "description_file_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DescriptionFile descriptionFileId;
    @Column(name = "user_info_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo userInfoId;
}
