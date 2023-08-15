package com.tms.repository;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DescriptionFileRepository extends JpaRepository<DescriptionFile, Integer> {
    List<DescriptionFile> findByCategories(Categories categories);

    List<DescriptionFile> findByGenre(Genre genre);

    DescriptionFile findDescriptionFileByNameFile(String nameFile);

    @Modifying
    @Query(nativeQuery = true, value = "from DescriptionFile where id = :id")
    Optional<DescriptionFile> findById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "insert into description_file(path_to_file) VALUES (?)")
    void creatPathToFile(String pathToFile);
}
