package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.YahoofinanceApp;
import com.burynyk.yahoofinance.domain.Company;
import com.burynyk.yahoofinance.domain.Chart;
import com.burynyk.yahoofinance.repository.CompanyRepository;
import com.burynyk.yahoofinance.service.CompanyService;
import com.burynyk.yahoofinance.service.dto.CompanyCriteria;
import com.burynyk.yahoofinance.service.CompanyQueryService;

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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = YahoofinanceApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY = "BBBBBBBBBB";

    private static final Double DEFAULT_MARKET_CAP = 1D;
    private static final Double UPDATED_MARKET_CAP = 2D;
    private static final Double SMALLER_MARKET_CAP = 1D - 1D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyQueryService companyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyId(DEFAULT_COMPANY_ID)
            .companyName(DEFAULT_COMPANY_NAME)
            .industry(DEFAULT_INDUSTRY)
            .marketCap(DEFAULT_MARKET_CAP)
            .price(DEFAULT_PRICE);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .companyId(UPDATED_COMPANY_ID)
            .companyName(UPDATED_COMPANY_NAME)
            .industry(UPDATED_INDUSTRY)
            .marketCap(UPDATED_MARKET_CAP)
            .price(UPDATED_PRICE);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testCompany.getMarketCap()).isEqualTo(DEFAULT_MARKET_CAP);
        assertThat(testCompany.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCompanyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCompanyId(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCompanyName(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndustryIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setIndustry(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarketCapIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setMarketCap(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setPrice(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY)))
            .andExpect(jsonPath("$.[*].marketCap").value(hasItem(DEFAULT_MARKET_CAP.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.industry").value(DEFAULT_INDUSTRY))
            .andExpect(jsonPath("$.marketCap").value(DEFAULT_MARKET_CAP.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }


    @Test
    @Transactional
    public void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId equals to DEFAULT_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the companyList where companyId equals to UPDATED_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId not equals to DEFAULT_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);

        // Get all the companyList where companyId not equals to UPDATED_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the companyList where companyId equals to UPDATED_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId is not null
        defaultCompanyShouldBeFound("companyId.specified=true");

        // Get all the companyList where companyId is null
        defaultCompanyShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the companyList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the companyList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId is less than DEFAULT_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the companyList where companyId is less than UPDATED_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyId is greater than DEFAULT_COMPANY_ID
        defaultCompanyShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the companyList where companyId is greater than SMALLER_COMPANY_ID
        defaultCompanyShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }


    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName not equals to DEFAULT_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.notEquals=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName not equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.notEquals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the companyList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName is not null
        defaultCompanyShouldBeFound("companyName.specified=true");

        // Get all the companyList where companyName is null
        defaultCompanyShouldNotBeFound("companyName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName contains DEFAULT_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName contains UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName does not contain UPDATED_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }


    @Test
    @Transactional
    public void getAllCompaniesByIndustryIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where industry equals to DEFAULT_INDUSTRY
        defaultCompanyShouldBeFound("industry.equals=" + DEFAULT_INDUSTRY);

        // Get all the companyList where industry equals to UPDATED_INDUSTRY
        defaultCompanyShouldNotBeFound("industry.equals=" + UPDATED_INDUSTRY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIndustryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where industry not equals to DEFAULT_INDUSTRY
        defaultCompanyShouldNotBeFound("industry.notEquals=" + DEFAULT_INDUSTRY);

        // Get all the companyList where industry not equals to UPDATED_INDUSTRY
        defaultCompanyShouldBeFound("industry.notEquals=" + UPDATED_INDUSTRY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIndustryIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where industry in DEFAULT_INDUSTRY or UPDATED_INDUSTRY
        defaultCompanyShouldBeFound("industry.in=" + DEFAULT_INDUSTRY + "," + UPDATED_INDUSTRY);

        // Get all the companyList where industry equals to UPDATED_INDUSTRY
        defaultCompanyShouldNotBeFound("industry.in=" + UPDATED_INDUSTRY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIndustryIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where industry is not null
        defaultCompanyShouldBeFound("industry.specified=true");

        // Get all the companyList where industry is null
        defaultCompanyShouldNotBeFound("industry.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByIndustryContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where industry contains DEFAULT_INDUSTRY
        defaultCompanyShouldBeFound("industry.contains=" + DEFAULT_INDUSTRY);

        // Get all the companyList where industry contains UPDATED_INDUSTRY
        defaultCompanyShouldNotBeFound("industry.contains=" + UPDATED_INDUSTRY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIndustryNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where industry does not contain DEFAULT_INDUSTRY
        defaultCompanyShouldNotBeFound("industry.doesNotContain=" + DEFAULT_INDUSTRY);

        // Get all the companyList where industry does not contain UPDATED_INDUSTRY
        defaultCompanyShouldBeFound("industry.doesNotContain=" + UPDATED_INDUSTRY);
    }


    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap equals to DEFAULT_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.equals=" + DEFAULT_MARKET_CAP);

        // Get all the companyList where marketCap equals to UPDATED_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.equals=" + UPDATED_MARKET_CAP);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap not equals to DEFAULT_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.notEquals=" + DEFAULT_MARKET_CAP);

        // Get all the companyList where marketCap not equals to UPDATED_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.notEquals=" + UPDATED_MARKET_CAP);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap in DEFAULT_MARKET_CAP or UPDATED_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.in=" + DEFAULT_MARKET_CAP + "," + UPDATED_MARKET_CAP);

        // Get all the companyList where marketCap equals to UPDATED_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.in=" + UPDATED_MARKET_CAP);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap is not null
        defaultCompanyShouldBeFound("marketCap.specified=true");

        // Get all the companyList where marketCap is null
        defaultCompanyShouldNotBeFound("marketCap.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap is greater than or equal to DEFAULT_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.greaterThanOrEqual=" + DEFAULT_MARKET_CAP);

        // Get all the companyList where marketCap is greater than or equal to UPDATED_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.greaterThanOrEqual=" + UPDATED_MARKET_CAP);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap is less than or equal to DEFAULT_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.lessThanOrEqual=" + DEFAULT_MARKET_CAP);

        // Get all the companyList where marketCap is less than or equal to SMALLER_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.lessThanOrEqual=" + SMALLER_MARKET_CAP);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap is less than DEFAULT_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.lessThan=" + DEFAULT_MARKET_CAP);

        // Get all the companyList where marketCap is less than UPDATED_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.lessThan=" + UPDATED_MARKET_CAP);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMarketCapIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where marketCap is greater than DEFAULT_MARKET_CAP
        defaultCompanyShouldNotBeFound("marketCap.greaterThan=" + DEFAULT_MARKET_CAP);

        // Get all the companyList where marketCap is greater than SMALLER_MARKET_CAP
        defaultCompanyShouldBeFound("marketCap.greaterThan=" + SMALLER_MARKET_CAP);
    }


    @Test
    @Transactional
    public void getAllCompaniesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price equals to DEFAULT_PRICE
        defaultCompanyShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the companyList where price equals to UPDATED_PRICE
        defaultCompanyShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price not equals to DEFAULT_PRICE
        defaultCompanyShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the companyList where price not equals to UPDATED_PRICE
        defaultCompanyShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultCompanyShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the companyList where price equals to UPDATED_PRICE
        defaultCompanyShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price is not null
        defaultCompanyShouldBeFound("price.specified=true");

        // Get all the companyList where price is null
        defaultCompanyShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price is greater than or equal to DEFAULT_PRICE
        defaultCompanyShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the companyList where price is greater than or equal to UPDATED_PRICE
        defaultCompanyShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price is less than or equal to DEFAULT_PRICE
        defaultCompanyShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the companyList where price is less than or equal to SMALLER_PRICE
        defaultCompanyShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price is less than DEFAULT_PRICE
        defaultCompanyShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the companyList where price is less than UPDATED_PRICE
        defaultCompanyShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where price is greater than DEFAULT_PRICE
        defaultCompanyShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the companyList where price is greater than SMALLER_PRICE
        defaultCompanyShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllCompaniesByChartsIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Chart charts = ChartResourceIT.createEntity(em);
        em.persist(charts);
        em.flush();
        company.addCharts(charts);
        companyRepository.saveAndFlush(company);
        Long chartsId = charts.getId();

        // Get all the companyList where charts equals to chartsId
        defaultCompanyShouldBeFound("chartsId.equals=" + chartsId);

        // Get all the companyList where charts equals to chartsId + 1
        defaultCompanyShouldNotBeFound("chartsId.equals=" + (chartsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY)))
            .andExpect(jsonPath("$.[*].marketCap").value(hasItem(DEFAULT_MARKET_CAP.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyId(UPDATED_COMPANY_ID)
            .companyName(UPDATED_COMPANY_NAME)
            .industry(UPDATED_INDUSTRY)
            .marketCap(UPDATED_MARKET_CAP)
            .price(UPDATED_PRICE);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testCompany.getMarketCap()).isEqualTo(UPDATED_MARKET_CAP);
        assertThat(testCompany.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
