package com.burynyk.yahoofinance.repository;

import com.burynyk.yahoofinance.domain.SavedChart;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SavedChart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SavedChartRepository extends JpaRepository<SavedChart, Long> {
}
