package com.burynyk.yahoofinance.service;

import com.burynyk.yahoofinance.domain.SavedChart;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SavedChart}.
 */
public interface SavedChartService {

    /**
     * Save a savedChart.
     *
     * @param savedChart the entity to save.
     * @return the persisted entity.
     */
    SavedChart save(SavedChart savedChart);

    /**
     * Get all the savedCharts.
     *
     * @return the list of entities.
     */
    List<SavedChart> findAll();

    /**
     * Get the "id" savedChart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SavedChart> findOne(Long id);

    /**
     * Delete the "id" savedChart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
