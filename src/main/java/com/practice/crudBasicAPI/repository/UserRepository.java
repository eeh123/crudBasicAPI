package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Products;
import com.practice.crudBasicAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "SELECT * FROM tbl_users WHERE status = 1",
            nativeQuery = true)
    List<Users> getActiveUsers();

    @Query(value = "SELECT * FROM tbl_users WHERE email = ?1 && password = ?2",
            nativeQuery = true)
    Optional<Users> checkUserCred(String email, String password);

    @Query(value = "SELECT * FROM tbl_users WHERE isLoggedIn = true",
            nativeQuery = true)
    Optional<Users> checkLoggedInUser();

    @Modifying
    @Query(value = "UPDATE tbl_users SET isLoggedIn = ?1 WHERE email = ?2",
            nativeQuery = true)
    void setIsLoggedIn(boolean isLoggedIn, String email);

    Optional<Users> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE tbl_users SET status = 0 WHERE id = ?1",
            nativeQuery = true)
    void softDeleteUser(int id);
}

