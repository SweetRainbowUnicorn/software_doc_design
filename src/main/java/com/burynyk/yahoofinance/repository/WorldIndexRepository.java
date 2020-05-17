package com.burynyk.yahoofinance.repository;

import com.burynyk.yahoofinance.domain.WorldIndex;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorldIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorldIndexRepository extends JpaRepository<WorldIndex, Long> {
}
