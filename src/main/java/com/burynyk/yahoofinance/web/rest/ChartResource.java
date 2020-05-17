package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.domain.Chart;
import com.burynyk.yahoofinance.service.ChartService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.burynyk.yahoofinance.domain.Chart}.
 */
@RestController
@RequestMapping("/api")
public class ChartResource {

    private final Logger log = LoggerFactory.getLogger(ChartResource.class);

    private static final String ENTITY_NAME = "chart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChartService chartService;

    public ChartResource(ChartService chartService) {
        this.chartService = chartService;
    }

    /**
     * {@code POST  /charts} : Create a new chart.
     *
     * @param chart the chart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chart, or with status {@code 400 (Bad Request)} if the chart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charts")
    public ResponseEntity<Chart> createChart(@Valid @RequestBody Chart chart) throws URISyntaxException {
        log.debug("REST request to save Chart : {}", chart);
        if (chart.getId() != null) {
            throw new BadRequestAlertException("A new chart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chart result = chartService.save(chart);
        return ResponseEntity.created(new URI("/api/charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charts} : Updates an existing chart.
     *
     * @param chart the chart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chart,
     * or with status {@code 400 (Bad Request)} if the chart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charts")
    public ResponseEntity<Chart> updateChart(@Valid @RequestBody Chart chart) throws URISyntaxException {
        log.debug("REST request to update Chart : {}", chart);
        if (chart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chart result = chartService.save(chart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chart.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /charts} : get all the charts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of charts in body.
     */
    @GetMapping("/charts")
    public List<Chart> getAllCharts(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("savedchart-is-null".equals(filter)) {
            log.debug("REST request to get all Charts where savedChart is null");
            return chartService.findAllWhereSavedChartIsNull();
        }
        log.debug("REST request to get all Charts");
        return chartService.findAll();
    }

    /**
     * {@code GET  /charts/:id} : get the "id" chart.
     *
     * @param id the id of the chart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charts/{id}")
    public ResponseEntity<Chart> getChart(@PathVariable Long id) {
        log.debug("REST request to get Chart : {}", id);
        Optional<Chart> chart = chartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chart);
    }

    /**
     * {@code DELETE  /charts/:id} : delete the "id" chart.
     *
     * @param id the id of the chart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charts/{id}")
    public ResponseEntity<Void> deleteChart(@PathVariable Long id) {
        log.debug("REST request to delete Chart : {}", id);
        chartService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
