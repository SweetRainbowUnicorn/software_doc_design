package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.domain.YahooUser;
import com.burynyk.yahoofinance.service.YahooUserService;
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
 * REST controller for managing {@link com.burynyk.yahoofinance.domain.YahooUser}.
 */
@RestController
@RequestMapping("/api")
public class YahooUserResource {

    private final Logger log = LoggerFactory.getLogger(YahooUserResource.class);

    private static final String ENTITY_NAME = "yahooUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final YahooUserService yahooUserService;

    public YahooUserResource(YahooUserService yahooUserService) {
        this.yahooUserService = yahooUserService;
    }

    /**
     * {@code POST  /yahoo-users} : Create a new yahooUser.
     *
     * @param yahooUser the yahooUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new yahooUser, or with status {@code 400 (Bad Request)} if the yahooUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/yahoo-users")
    public ResponseEntity<YahooUser> createYahooUser(@Valid @RequestBody YahooUser yahooUser) throws URISyntaxException {
        log.debug("REST request to save YahooUser : {}", yahooUser);
        if (yahooUser.getId() != null) {
            throw new BadRequestAlertException("A new yahooUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        YahooUser result = yahooUserService.save(yahooUser);
        return ResponseEntity.created(new URI("/api/yahoo-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /yahoo-users} : Updates an existing yahooUser.
     *
     * @param yahooUser the yahooUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated yahooUser,
     * or with status {@code 400 (Bad Request)} if the yahooUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the yahooUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/yahoo-users")
    public ResponseEntity<YahooUser> updateYahooUser(@Valid @RequestBody YahooUser yahooUser) throws URISyntaxException {
        log.debug("REST request to update YahooUser : {}", yahooUser);
        if (yahooUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        YahooUser result = yahooUserService.save(yahooUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, yahooUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /yahoo-users} : get all the yahooUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of yahooUsers in body.
     */
    @GetMapping("/yahoo-users")
    public List<YahooUser> getAllYahooUsers() {
        log.debug("REST request to get all YahooUsers");
        return yahooUserService.findAll();
    }

    /**
     * {@code GET  /yahoo-users/:id} : get the "id" yahooUser.
     *
     * @param id the id of the yahooUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the yahooUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/yahoo-users/{id}")
    public ResponseEntity<YahooUser> getYahooUser(@PathVariable Long id) {
        log.debug("REST request to get YahooUser : {}", id);
        Optional<YahooUser> yahooUser = yahooUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(yahooUser);
    }

    /**
     * {@code DELETE  /yahoo-users/:id} : delete the "id" yahooUser.
     *
     * @param id the id of the yahooUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/yahoo-users/{id}")
    public ResponseEntity<Void> deleteYahooUser(@PathVariable Long id) {
        log.debug("REST request to delete YahooUser : {}", id);
        yahooUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
