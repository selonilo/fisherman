package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
}
