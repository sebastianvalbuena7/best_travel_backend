package com.sebastian.bestTravel.domain.repositories;

import com.sebastian.bestTravel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {
    //    @Query("select fly from fly where fly.price < :price")
    //    Set<FlyEntity> selectLessPrice(@Param("price") BigDecimal name);

    //    @Query(value = "select fly from fly where fly.price < :price", nativeQuery = true)
    //    Set<FlyEntity> selectLessPrice(BigDecimal price);

    @Query("select f from fly f where f.price < :price")
    Set<FlyEntity> selectLessPrice(BigDecimal price);

    @Query("select f from fly f where f.price between :min and :max")
    Set<FlyEntity> selectBetweenPrice(BigDecimal min, BigDecimal max);

    @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny")
    Set<FlyEntity> selectOriginDestiny(String origin, String destiny);

    @Query("select f from fly f join fetch f.ticket t where t.id = :id")
    Optional<FlyEntity> findByTicketId(UUID id);
}