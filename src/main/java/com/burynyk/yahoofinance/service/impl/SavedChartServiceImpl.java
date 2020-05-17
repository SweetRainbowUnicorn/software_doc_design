package com.burynyk.yahoofinance.service.impl;

import com.burynyk.yahoofinance.service.SavedChartService;
import com.burynyk.yahoofinance.domain.SavedChart;
import com.burynyk.yahoofinance.repository.SavedChartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SavedChart}.
 */
@Service
@Transactional
public class SavedChartServiceImpl implements SavedChartService {

    private final Logger log = LoggerFactory.getLogger(SavedChartServiceImpl.class);

    private final SavedChartRepository savedChartRepository;

    public SavedChartServiceImpl(SavedChartRepository savedChartRepository) {
        this.savedChartRepository = savedChartRepository;
    }

    /**
     * Save a savedChart.
     *
     * @param savedChart the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SavedChart save(SavedChart savedChart) {
        log.debug("Request to save SavedChart : {}", savedChart);
        return savedChartRepository.save(savedChart);
    }

    /**
     * Get all the savedCharts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SavedChart> findAll() {
        log.debug("Request to get all SavedCharts");
        return savedChartRepository.findAll();
    }

    /**
     * Get one savedChart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SavedChart> findOne(Long id) {
        log.debug("Request to get SavedChart : {}", id);
        return savedChartRepository.findById(id);
    }

    /**
     * Delete the savedChart by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SavedChart : {}", id);
        savedChartRepository.deleteById(id);
    }
}
