package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.domain.SavedChart;
import com.burynyk.yahoofinance.service.SavedChartService;
import com.burynyk.yahoofinance.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.burynyk.yahoofinance.domain.SavedChart}.
 */
@RestController
@RequestMapping("/api")
public class SavedChartResource {

    private final Logger log = LoggerFactory.getLogger(SavedChartResource.class);

    private static final String ENTITY_NAME = "savedChart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SavedChartService savedChartService;

    public SavedChartResource(SavedChartService savedChartService) {
        this.savedChartService = savedChartService;
    }

    /**
     * {@code POST  /saved-charts} : Create a new savedChart.
     *
     * @param savedChart the savedChart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new savedChart, or with status {@code 400 (Bad Request)} if the savedChart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saved-charts")
    public ResponseEntity<SavedChart> createSavedChart(@Valid @RequestBody SavedChart savedChart) throws URISyntaxException {
        log.debug("REST request to save SavedChart : {}", savedChart);
        if (savedChart.getId() != null) {
            throw new BadRequestAlertException("A new savedChart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SavedChart result = savedChartService.save(savedChart);
        return ResponseEntity.created(new URI("/api/saved-charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saved-charts} : Updates an existing savedChart.
     *
     * @param savedChart the savedChart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated savedChart,
     * or with status {@code 400 (Bad Request)} if the savedChart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the savedChart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saved-charts")
    public ResponseEntity<SavedChart> updateSavedChart(@Valid @RequestBody SavedChart savedChart) throws URISyntaxException {
        log.debug("REST request to update SavedChart : {}", savedChart);
        if (savedChart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SavedChart result = savedChartService.save(savedChart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, savedChart.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /saved-charts} : get all the savedCharts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of savedCharts in body.
     */
    @GetMapping("/saved-charts")
    public List<SavedChart> getAllSavedCharts() {
        log.debug("REST request to get all SavedCharts");
        return savedChartService.findAll();
    }

    /**
     * {@code GET  /saved-charts/:id} : get the "id" savedChart.
     *
     * @param id the id of the savedChart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the savedChart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saved-charts/{id}")
    public ResponseEntity<SavedChart> getSavedChart(@PathVariable Long id) {
        log.debug("REST request to get SavedChart : {}", id);
        Optional<SavedChart> savedChart = savedChartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(savedChart);
    }

    /**
     * {@code DELETE  /saved-charts/:id} : delete the "id" savedChart.
     *
     * @param id the id of the savedChart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saved-charts/{id}")
    public ResponseEntity<Void> deleteSavedChart(@PathVariable Long id) {
        log.debug("REST request to delete SavedChart : {}", id);
        savedChartService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
