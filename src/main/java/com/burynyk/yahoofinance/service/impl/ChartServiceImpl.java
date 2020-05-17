package com.burynyk.yahoofinance.service.impl;

import com.burynyk.yahoofinance.service.ChartService;
import com.burynyk.yahoofinance.domain.Chart;
import com.burynyk.yahoofinance.repository.ChartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Chart}.
 */
@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    private final Logger log = LoggerFactory.getLogger(ChartServiceImpl.class);

    private final ChartRepository chartRepository;

    public ChartServiceImpl(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    /**
     * Save a chart.
     *
     * @param chart the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Chart save(Chart chart) {
        log.debug("Request to save Chart : {}", chart);
        return chartRepository.save(chart);
    }

    /**
     * Get all the charts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Chart> findAll() {
        log.debug("Request to get all Charts");
        return chartRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the charts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Chart> findAllWithEagerRelationships(Pageable pageable) {
        return chartRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     *  Get all the charts where SavedChart is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Chart> findAllWhereSavedChartIsNull() {
        log.debug("Request to get all charts where SavedChart is null");
        return StreamSupport
            .stream(chartRepository.findAll().spliterator(), false)
            .filter(chart -> chart.getSavedChart() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one chart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Chart> findOne(Long id) {
        log.debug("Request to get Chart : {}", id);
        return chartRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the chart by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chart : {}", id);
        chartRepository.deleteById(id);
    }
}
