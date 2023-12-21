package com.github.gather.repositroy;


import com.github.gather.entity.User;
import com.github.gather.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email =:email")
    UserDetails findUserDetailByEmail(@Param("email") String email);

    @Query("SELECT ug FROM UserGroup ug WHERE :user MEMBER OF ug.users")
    List<UserGroup> findAllUserGroupsByUser(@Param("user") User userToBookmark);

//    @Transactional
//    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email =:email")
//    Optional<User> findByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);
}

