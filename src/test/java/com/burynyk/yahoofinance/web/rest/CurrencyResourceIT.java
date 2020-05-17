package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.YahoofinanceApp;
import com.burynyk.yahoofinance.domain.Currency;
import com.burynyk.yahoofinance.domain.Chart;
import com.burynyk.yahoofinance.repository.CurrencyRepository;
import com.burynyk.yahoofinance.service.CurrencyService;
import com.burynyk.yahoofinance.service.dto.CurrencyCriteria;
import com.burynyk.yahoofinance.service.CurrencyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurrencyResource} REST controller.
 */
@SpringBootTest(classes = YahoofinanceApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CurrencyResourceIT {

    private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final Double DEFAULT_LAST_PRICE = 1D;
    private static final Double UPDATED_LAST_PRICE = 2D;
    private static final Double SMALLER_LAST_PRICE = 1D - 1D;

    private static final Double DEFAULT_CHANGE = 1D;
    private static final Double UPDATED_CHANGE = 2D;
    private static final Double SMALLER_CHANGE = 1D - 1D;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyQueryService currencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .currencyName(DEFAULT_CURRENCY_NAME)
            .symbol(DEFAULT_SYMBOL)
            .lastPrice(DEFAULT_LAST_PRICE)
            .change(DEFAULT_CHANGE);
        return currency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .currencyName(UPDATED_CURRENCY_NAME)
            .symbol(UPDATED_SYMBOL)
            .lastPrice(UPDATED_LAST_PRICE)
            .change(UPDATED_CHANGE);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCurrency.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCurrency.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testCurrency.getChange()).isEqualTo(DEFAULT_CHANGE);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrencyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setCurrencyName(null);

        // Create the Currency, which fails.

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setSymbol(null);

        // Create the Currency, which fails.

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setLastPrice(null);

        // Create the Currency, which fails.

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setChange(null);

        // Create the Currency, which fails.

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].change").value(hasItem(DEFAULT_CHANGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE.doubleValue()))
            .andExpect(jsonPath("$.change").value(DEFAULT_CHANGE.doubleValue()));
    }


    @Test
    @Transactional
    public void getCurrenciesByIdFiltering() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        Long id = currency.getId();

        defaultCurrencyShouldBeFound("id.equals=" + id);
        defaultCurrencyShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.equals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.equals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName not equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.notEquals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName not equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.notEquals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName in DEFAULT_CURRENCY_NAME or UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.in=" + DEFAULT_CURRENCY_NAME + "," + UPDATED_CURRENCY_NAME);

        // Get all the currencyList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.in=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName is not null
        defaultCurrencyShouldBeFound("currencyName.specified=true");

        // Get all the currencyList where currencyName is null
        defaultCurrencyShouldNotBeFound("currencyName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName contains DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.contains=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName contains UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.contains=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName does not contain DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.doesNotContain=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName does not contain UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.doesNotContain=" + UPDATED_CURRENCY_NAME);
    }


    @Test
    @Transactional
    public void getAllCurrenciesBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where symbol equals to DEFAULT_SYMBOL
        defaultCurrencyShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the currencyList where symbol equals to UPDATED_SYMBOL
        defaultCurrencyShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySymbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where symbol not equals to DEFAULT_SYMBOL
        defaultCurrencyShouldNotBeFound("symbol.notEquals=" + DEFAULT_SYMBOL);

        // Get all the currencyList where symbol not equals to UPDATED_SYMBOL
        defaultCurrencyShouldBeFound("symbol.notEquals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultCurrencyShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the currencyList where symbol equals to UPDATED_SYMBOL
        defaultCurrencyShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where symbol is not null
        defaultCurrencyShouldBeFound("symbol.specified=true");

        // Get all the currencyList where symbol is null
        defaultCurrencyShouldNotBeFound("symbol.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesBySymbolContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where symbol contains DEFAULT_SYMBOL
        defaultCurrencyShouldBeFound("symbol.contains=" + DEFAULT_SYMBOL);

        // Get all the currencyList where symbol contains UPDATED_SYMBOL
        defaultCurrencyShouldNotBeFound("symbol.contains=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where symbol does not contain DEFAULT_SYMBOL
        defaultCurrencyShouldNotBeFound("symbol.doesNotContain=" + DEFAULT_SYMBOL);

        // Get all the currencyList where symbol does not contain UPDATED_SYMBOL
        defaultCurrencyShouldBeFound("symbol.doesNotContain=" + UPDATED_SYMBOL);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice equals to DEFAULT_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.equals=" + DEFAULT_LAST_PRICE);

        // Get all the currencyList where lastPrice equals to UPDATED_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.equals=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice not equals to DEFAULT_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.notEquals=" + DEFAULT_LAST_PRICE);

        // Get all the currencyList where lastPrice not equals to UPDATED_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.notEquals=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice in DEFAULT_LAST_PRICE or UPDATED_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.in=" + DEFAULT_LAST_PRICE + "," + UPDATED_LAST_PRICE);

        // Get all the currencyList where lastPrice equals to UPDATED_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.in=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice is not null
        defaultCurrencyShouldBeFound("lastPrice.specified=true");

        // Get all the currencyList where lastPrice is null
        defaultCurrencyShouldNotBeFound("lastPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice is greater than or equal to DEFAULT_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.greaterThanOrEqual=" + DEFAULT_LAST_PRICE);

        // Get all the currencyList where lastPrice is greater than or equal to UPDATED_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.greaterThanOrEqual=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice is less than or equal to DEFAULT_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.lessThanOrEqual=" + DEFAULT_LAST_PRICE);

        // Get all the currencyList where lastPrice is less than or equal to SMALLER_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.lessThanOrEqual=" + SMALLER_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice is less than DEFAULT_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.lessThan=" + DEFAULT_LAST_PRICE);

        // Get all the currencyList where lastPrice is less than UPDATED_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.lessThan=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByLastPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where lastPrice is greater than DEFAULT_LAST_PRICE
        defaultCurrencyShouldNotBeFound("lastPrice.greaterThan=" + DEFAULT_LAST_PRICE);

        // Get all the currencyList where lastPrice is greater than SMALLER_LAST_PRICE
        defaultCurrencyShouldBeFound("lastPrice.greaterThan=" + SMALLER_LAST_PRICE);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change equals to DEFAULT_CHANGE
        defaultCurrencyShouldBeFound("change.equals=" + DEFAULT_CHANGE);

        // Get all the currencyList where change equals to UPDATED_CHANGE
        defaultCurrencyShouldNotBeFound("change.equals=" + UPDATED_CHANGE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change not equals to DEFAULT_CHANGE
        defaultCurrencyShouldNotBeFound("change.notEquals=" + DEFAULT_CHANGE);

        // Get all the currencyList where change not equals to UPDATED_CHANGE
        defaultCurrencyShouldBeFound("change.notEquals=" + UPDATED_CHANGE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change in DEFAULT_CHANGE or UPDATED_CHANGE
        defaultCurrencyShouldBeFound("change.in=" + DEFAULT_CHANGE + "," + UPDATED_CHANGE);

        // Get all the currencyList where change equals to UPDATED_CHANGE
        defaultCurrencyShouldNotBeFound("change.in=" + UPDATED_CHANGE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change is not null
        defaultCurrencyShouldBeFound("change.specified=true");

        // Get all the currencyList where change is null
        defaultCurrencyShouldNotBeFound("change.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change is greater than or equal to DEFAULT_CHANGE
        defaultCurrencyShouldBeFound("change.greaterThanOrEqual=" + DEFAULT_CHANGE);

        // Get all the currencyList where change is greater than or equal to UPDATED_CHANGE
        defaultCurrencyShouldNotBeFound("change.greaterThanOrEqual=" + UPDATED_CHANGE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change is less than or equal to DEFAULT_CHANGE
        defaultCurrencyShouldBeFound("change.lessThanOrEqual=" + DEFAULT_CHANGE);

        // Get all the currencyList where change is less than or equal to SMALLER_CHANGE
        defaultCurrencyShouldNotBeFound("change.lessThanOrEqual=" + SMALLER_CHANGE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change is less than DEFAULT_CHANGE
        defaultCurrencyShouldNotBeFound("change.lessThan=" + DEFAULT_CHANGE);

        // Get all the currencyList where change is less than UPDATED_CHANGE
        defaultCurrencyShouldBeFound("change.lessThan=" + UPDATED_CHANGE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByChangeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where change is greater than DEFAULT_CHANGE
        defaultCurrencyShouldNotBeFound("change.greaterThan=" + DEFAULT_CHANGE);

        // Get all the currencyList where change is greater than SMALLER_CHANGE
        defaultCurrencyShouldBeFound("change.greaterThan=" + SMALLER_CHANGE);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByChartsIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);
        Chart charts = ChartResourceIT.createEntity(em);
        em.persist(charts);
        em.flush();
        currency.addCharts(charts);
        currencyRepository.saveAndFlush(currency);
        Long chartsId = charts.getId();

        // Get all the currencyList where charts equals to chartsId
        defaultCurrencyShouldBeFound("chartsId.equals=" + chartsId);

        // Get all the currencyList where charts equals to chartsId + 1
        defaultCurrencyShouldNotBeFound("chartsId.equals=" + (chartsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].change").value(hasItem(DEFAULT_CHANGE.doubleValue())));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .currencyName(UPDATED_CURRENCY_NAME)
            .symbol(UPDATED_SYMBOL)
            .lastPrice(UPDATED_LAST_PRICE)
            .change(UPDATED_CHANGE);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrency)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrency.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCurrency.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testCurrency.getChange()).isEqualTo(UPDATED_CHANGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Create the Currency

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
