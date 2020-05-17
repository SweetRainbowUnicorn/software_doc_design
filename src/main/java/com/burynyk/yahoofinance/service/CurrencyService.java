package com.burynyk.yahoofinance.service;

import com.burynyk.yahoofinance.domain.Currency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Currency}.
 */
public interface CurrencyService {

    /**
     * Save a currency.
     *
     * @param currency the entity to save.
     * @return the persisted entity.
     */
    Currency save(Currency currency);

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Currency> findAll(Pageable pageable);

    /**
     * Get the "id" currency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Currency> findOne(Long id);

    /**
     * Delete the "id" currency.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
