package com.tms.repository;

import com.tms.models.Role;
import com.tms.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    List<UserInfo> findAllByRole(Role role);
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO L_user_file (id, user_info_id, description_file_id) VALUES (DEFAULT, :userInfoId, :descriptionFileId)")
    void addFileToUser(Integer userInfoId, Integer descriptionFileId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM L_user_file WHERE (id, user_info_id, description_file_id) VALUES (DEFAULT, :userInfoId, :descriptionFileId)")
    void deleteByFavoritesFile(Integer userInfoId, Integer descriptionFileId);
}
