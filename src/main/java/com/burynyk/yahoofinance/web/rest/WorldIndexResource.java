package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.domain.WorldIndex;
import com.burynyk.yahoofinance.service.WorldIndexService;
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
 * REST controller for managing {@link com.burynyk.yahoofinance.domain.WorldIndex}.
 */
@RestController
@RequestMapping("/api")
public class WorldIndexResource {

    private final Logger log = LoggerFactory.getLogger(WorldIndexResource.class);

    private static final String ENTITY_NAME = "worldIndex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorldIndexService worldIndexService;

    public WorldIndexResource(WorldIndexService worldIndexService) {
        this.worldIndexService = worldIndexService;
    }

    /**
     * {@code POST  /world-indices} : Create a new worldIndex.
     *
     * @param worldIndex the worldIndex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new worldIndex, or with status {@code 400 (Bad Request)} if the worldIndex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/world-indices")
    public ResponseEntity<WorldIndex> createWorldIndex(@Valid @RequestBody WorldIndex worldIndex) throws URISyntaxException {
        log.debug("REST request to save WorldIndex : {}", worldIndex);
        if (worldIndex.getId() != null) {
            throw new BadRequestAlertException("A new worldIndex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorldIndex result = worldIndexService.save(worldIndex);
        return ResponseEntity.created(new URI("/api/world-indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /world-indices} : Updates an existing worldIndex.
     *
     * @param worldIndex the worldIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worldIndex,
     * or with status {@code 400 (Bad Request)} if the worldIndex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the worldIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/world-indices")
    public ResponseEntity<WorldIndex> updateWorldIndex(@Valid @RequestBody WorldIndex worldIndex) throws URISyntaxException {
        log.debug("REST request to update WorldIndex : {}", worldIndex);
        if (worldIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorldIndex result = worldIndexService.save(worldIndex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, worldIndex.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /world-indices} : get all the worldIndices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of worldIndices in body.
     */
    @GetMapping("/world-indices")
    public List<WorldIndex> getAllWorldIndices() {
        log.debug("REST request to get all WorldIndices");
        return worldIndexService.findAll();
    }

    /**
     * {@code GET  /world-indices/:id} : get the "id" worldIndex.
     *
     * @param id the id of the worldIndex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the worldIndex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/world-indices/{id}")
    public ResponseEntity<WorldIndex> getWorldIndex(@PathVariable Long id) {
        log.debug("REST request to get WorldIndex : {}", id);
        Optional<WorldIndex> worldIndex = worldIndexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(worldIndex);
    }

    /**
     * {@code DELETE  /world-indices/:id} : delete the "id" worldIndex.
     *
     * @param id the id of the worldIndex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/world-indices/{id}")
    public ResponseEntity<Void> deleteWorldIndex(@PathVariable Long id) {
        log.debug("REST request to delete WorldIndex : {}", id);
        worldIndexService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
