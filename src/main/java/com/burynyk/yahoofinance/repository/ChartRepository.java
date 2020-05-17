package com.burynyk.yahoofinance.repository;

import com.burynyk.yahoofinance.domain.Chart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Chart entity.
 */
@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {

    @Query(value = "select distinct chart from Chart chart left join fetch chart.companies left join fetch chart.currencies left join fetch chart.indexes",
        countQuery = "select count(distinct chart) from Chart chart")
    Page<Chart> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct chart from Chart chart left join fetch chart.companies left join fetch chart.currencies left join fetch chart.indexes")
    List<Chart> findAllWithEagerRelationships();

    @Query("select chart from Chart chart left join fetch chart.companies left join fetch chart.currencies left join fetch chart.indexes where chart.id =:id")
    Optional<Chart> findOneWithEagerRelationships(@Param("id") Long id);
}
