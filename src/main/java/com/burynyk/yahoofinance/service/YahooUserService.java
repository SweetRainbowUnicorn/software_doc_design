package com.burynyk.yahoofinance.service;

import com.burynyk.yahoofinance.domain.YahooUser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link YahooUser}.
 */
public interface YahooUserService {

    /**
     * Save a yahooUser.
     *
     * @param yahooUser the entity to save.
     * @return the persisted entity.
     */
    YahooUser save(YahooUser yahooUser);

    /**
     * Get all the yahooUsers.
     *
     * @return the list of entities.
     */
    List<YahooUser> findAll();

    /**
     * Get the "id" yahooUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<YahooUser> findOne(Long id);

    /**
     * Delete the "id" yahooUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
