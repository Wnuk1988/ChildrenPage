package com.tms.repository;

import com.tms.models.DescriptionFile;
import com.tms.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    List<DescriptionFile> favoritesFile = new ArrayList<>();

    default void addFavoritesFile(DescriptionFile descriptionFile) {
        favoritesFile.add(descriptionFile);
    }

}
