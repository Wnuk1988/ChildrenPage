package com.tms.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@EqualsAndHashCode(exclude = {"favoritesFile"})
@ToString(exclude = {"favoritesFile"})
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "user_info_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "email")
    private String email;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="l_user_file",
            joinColumns=  @JoinColumn(name="user_info_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="description_file_id", referencedColumnName="id") )
    private List<DescriptionFile> favoritesFile = new ArrayList<>();
}
