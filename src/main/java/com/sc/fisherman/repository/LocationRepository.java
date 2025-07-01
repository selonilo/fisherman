package com.sc.fisherman.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sc.fisherman.model.dto.location.LocationQueryModel;
import com.sc.fisherman.model.entity.LocationEntity;
import com.sc.fisherman.model.entity.QLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long>, QuerydslPredicateExecutor<LocationEntity> {
}
