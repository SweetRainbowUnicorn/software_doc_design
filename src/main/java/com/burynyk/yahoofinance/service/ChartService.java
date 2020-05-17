package com.burynyk.yahoofinance.service;

import com.burynyk.yahoofinance.domain.Chart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Chart}.
 */
public interface ChartService {

    /**
     * Save a chart.
     *
     * @param chart the entity to save.
     * @return the persisted entity.
     */
    Chart save(Chart chart);

    /**
     * Get all the charts.
     *
     * @return the list of entities.
     */
    List<Chart> findAll();
    /**
     * Get all the ChartDTO where SavedChart is {@code null}.
     *
     * @return the list of entities.
     */
    List<Chart> findAllWhereSavedChartIsNull();

    /**
     * Get all the charts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Chart> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" chart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Chart> findOne(Long id);

    /**
     * Delete the "id" chart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
