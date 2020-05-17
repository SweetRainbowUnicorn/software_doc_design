package com.burynyk.yahoofinance.service.impl;

import com.burynyk.yahoofinance.service.YahooUserService;
import com.burynyk.yahoofinance.domain.YahooUser;
import com.burynyk.yahoofinance.repository.YahooUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link YahooUser}.
 */
@Service
@Transactional
public class YahooUserServiceImpl implements YahooUserService {

    private final Logger log = LoggerFactory.getLogger(YahooUserServiceImpl.class);

    private final YahooUserRepository yahooUserRepository;

    public YahooUserServiceImpl(YahooUserRepository yahooUserRepository) {
        this.yahooUserRepository = yahooUserRepository;
    }

    /**
     * Save a yahooUser.
     *
     * @param yahooUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public YahooUser save(YahooUser yahooUser) {
        log.debug("Request to save YahooUser : {}", yahooUser);
        return yahooUserRepository.save(yahooUser);
    }

    /**
     * Get all the yahooUsers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<YahooUser> findAll() {
        log.debug("Request to get all YahooUsers");
        return yahooUserRepository.findAll();
    }

    /**
     * Get one yahooUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<YahooUser> findOne(Long id) {
        log.debug("Request to get YahooUser : {}", id);
        return yahooUserRepository.findById(id);
    }

    /**
     * Delete the yahooUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete YahooUser : {}", id);
        yahooUserRepository.deleteById(id);
    }
}
