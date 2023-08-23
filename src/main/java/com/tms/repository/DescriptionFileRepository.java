package com.tms.repository;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionFileRepository extends JpaRepository<DescriptionFile, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO description_file (path_to_file) VALUES (:pathToFile)")
    void creatPathToFile(String pathToFile);

    List<DescriptionFile> findByCategories(Categories categories);

    List<DescriptionFile> findByGenre(Genre genre);

    void deleteById(Integer id);
}
