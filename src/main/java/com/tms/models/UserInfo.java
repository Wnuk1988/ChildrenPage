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
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="L_user_file",
            joinColumns=  @JoinColumn(name="user_info_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="description_file_id", referencedColumnName="id") )
    private List<DescriptionFile> favoritesFile = new ArrayList<>();
}
