package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByMail(String mail);
    List<UserEntity> findByIdIn(List<Long> id);

    @Query("SELECT p FROM UserEntity p WHERE (:name IS NULL OR :name = '' OR UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%')))")
    Page<UserEntity> findAllPost(@Param("name") String name, Pageable pageable);
}
