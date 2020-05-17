package com.burynyk.yahoofinance.service;

import com.burynyk.yahoofinance.domain.WorldIndex;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorldIndex}.
 */
public interface WorldIndexService {

    /**
     * Save a worldIndex.
     *
     * @param worldIndex the entity to save.
     * @return the persisted entity.
     */
    WorldIndex save(WorldIndex worldIndex);

    /**
     * Get all the worldIndices.
     *
     * @return the list of entities.
     */
    List<WorldIndex> findAll();

    /**
     * Get the "id" worldIndex.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorldIndex> findOne(Long id);

    /**
     * Delete the "id" worldIndex.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
