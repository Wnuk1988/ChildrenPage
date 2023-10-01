package com.tms.repository;

import com.tms.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO l_user_file (id, user_info_id, description_file_id) VALUES (DEFAULT, :userInfoId, :descriptionFileId)")
    void addFileToUser(Integer userInfoId, Integer descriptionFileId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM l_user_file WHERE user_info_id=:userInfoId AND description_file_id=:descriptionFileId")
    void deleteByFavoritesFile(Integer userInfoId, Integer descriptionFileId);
}
