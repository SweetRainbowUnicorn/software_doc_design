package com.burynyk.yahoofinance.service.impl;

import com.burynyk.yahoofinance.service.WorldIndexService;
import com.burynyk.yahoofinance.domain.WorldIndex;
import com.burynyk.yahoofinance.repository.WorldIndexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WorldIndex}.
 */
@Service
@Transactional
public class WorldIndexServiceImpl implements WorldIndexService {

    private final Logger log = LoggerFactory.getLogger(WorldIndexServiceImpl.class);

    private final WorldIndexRepository worldIndexRepository;

    public WorldIndexServiceImpl(WorldIndexRepository worldIndexRepository) {
        this.worldIndexRepository = worldIndexRepository;
    }

    /**
     * Save a worldIndex.
     *
     * @param worldIndex the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorldIndex save(WorldIndex worldIndex) {
        log.debug("Request to save WorldIndex : {}", worldIndex);
        return worldIndexRepository.save(worldIndex);
    }

    /**
     * Get all the worldIndices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorldIndex> findAll() {
        log.debug("Request to get all WorldIndices");
        return worldIndexRepository.findAll();
    }

    /**
     * Get one worldIndex by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorldIndex> findOne(Long id) {
        log.debug("Request to get WorldIndex : {}", id);
        return worldIndexRepository.findById(id);
    }

    /**
     * Delete the worldIndex by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorldIndex : {}", id);
        worldIndexRepository.deleteById(id);
    }
}
