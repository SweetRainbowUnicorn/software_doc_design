package com.burynyk.yahoofinance.repository;

import com.burynyk.yahoofinance.domain.YahooUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the YahooUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YahooUserRepository extends JpaRepository<YahooUser, Long> {
}
