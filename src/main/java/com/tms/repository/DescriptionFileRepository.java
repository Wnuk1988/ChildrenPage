package com.tms.repository;

import com.tms.models.Categories;
import com.tms.models.DescriptionFile;
import com.tms.models.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionFileRepository extends JpaRepository<DescriptionFile,Integer> {
    List<DescriptionFile> findByCategories(Categories categories);

    List<DescriptionFile> findByGenre(Genre genre);

    DescriptionFile findDescriptionFileByNameFile(String nameFile);

//    @Modifying
//    @Transactional
//    @Query("delete from DescriptionFile id where DescriptionFile.id=:id")
//    int deleteDescriptionFileById(Integer id);
}
