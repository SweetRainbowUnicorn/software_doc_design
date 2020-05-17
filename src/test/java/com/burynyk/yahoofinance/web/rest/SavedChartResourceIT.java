package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.YahoofinanceApp;
import com.burynyk.yahoofinance.domain.SavedChart;
import com.burynyk.yahoofinance.repository.SavedChartRepository;
import com.burynyk.yahoofinance.service.SavedChartService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.burynyk.yahoofinance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SavedChartResource} REST controller.
 */
@SpringBootTest(classes = YahoofinanceApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SavedChartResourceIT {

    private static final Long DEFAULT_CHART_ID = 1L;
    private static final Long UPDATED_CHART_ID = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;

    private static final Long DEFAULT_INDICATOR = 1L;
    private static final Long UPDATED_INDICATOR = 2L;

    private static final Double DEFAULT_X_AXIS_STEP = 1D;
    private static final Double UPDATED_X_AXIS_STEP = 2D;

    private static final Double DEFAULT_Y_AXIS_STEP = 1D;
    private static final Double UPDATED_Y_AXIS_STEP = 2D;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SavedChartRepository savedChartRepository;

    @Autowired
    private SavedChartService savedChartService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSavedChartMockMvc;

    private SavedChart savedChart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedChart createEntity(EntityManager em) {
        SavedChart savedChart = new SavedChart()
            .chartId(DEFAULT_CHART_ID)
            .userId(DEFAULT_USER_ID)
            .itemId(DEFAULT_ITEM_ID)
            .indicator(DEFAULT_INDICATOR)
            .xAxisStep(DEFAULT_X_AXIS_STEP)
            .yAxisStep(DEFAULT_Y_AXIS_STEP)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return savedChart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedChart createUpdatedEntity(EntityManager em) {
        SavedChart savedChart = new SavedChart()
            .chartId(UPDATED_CHART_ID)
            .userId(UPDATED_USER_ID)
            .itemId(UPDATED_ITEM_ID)
            .indicator(UPDATED_INDICATOR)
            .xAxisStep(UPDATED_X_AXIS_STEP)
            .yAxisStep(UPDATED_Y_AXIS_STEP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return savedChart;
    }

    @BeforeEach
    public void initTest() {
        savedChart = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedChart() throws Exception {
        int databaseSizeBeforeCreate = savedChartRepository.findAll().size();

        // Create the SavedChart
        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isCreated());

        // Validate the SavedChart in the database
        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeCreate + 1);
        SavedChart testSavedChart = savedChartList.get(savedChartList.size() - 1);
        assertThat(testSavedChart.getChartId()).isEqualTo(DEFAULT_CHART_ID);
        assertThat(testSavedChart.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSavedChart.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testSavedChart.getIndicator()).isEqualTo(DEFAULT_INDICATOR);
        assertThat(testSavedChart.getxAxisStep()).isEqualTo(DEFAULT_X_AXIS_STEP);
        assertThat(testSavedChart.getyAxisStep()).isEqualTo(DEFAULT_Y_AXIS_STEP);
        assertThat(testSavedChart.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSavedChart.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createSavedChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = savedChartRepository.findAll().size();

        // Create the SavedChart with an existing ID
        savedChart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        // Validate the SavedChart in the database
        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChartIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setChartId(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setUserId(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setItemId(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndicatorIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setIndicator(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkxAxisStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setxAxisStep(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkyAxisStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setyAxisStep(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setStartDate(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = savedChartRepository.findAll().size();
        // set the field null
        savedChart.setEndDate(null);

        // Create the SavedChart, which fails.

        restSavedChartMockMvc.perform(post("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSavedCharts() throws Exception {
        // Initialize the database
        savedChartRepository.saveAndFlush(savedChart);

        // Get all the savedChartList
        restSavedChartMockMvc.perform(get("/api/saved-charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(savedChart.getId().intValue())))
            .andExpect(jsonPath("$.[*].chartId").value(hasItem(DEFAULT_CHART_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].indicator").value(hasItem(DEFAULT_INDICATOR.intValue())))
            .andExpect(jsonPath("$.[*].xAxisStep").value(hasItem(DEFAULT_X_AXIS_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].yAxisStep").value(hasItem(DEFAULT_Y_AXIS_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }
    
    @Test
    @Transactional
    public void getSavedChart() throws Exception {
        // Initialize the database
        savedChartRepository.saveAndFlush(savedChart);

        // Get the savedChart
        restSavedChartMockMvc.perform(get("/api/saved-charts/{id}", savedChart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(savedChart.getId().intValue()))
            .andExpect(jsonPath("$.chartId").value(DEFAULT_CHART_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.indicator").value(DEFAULT_INDICATOR.intValue()))
            .andExpect(jsonPath("$.xAxisStep").value(DEFAULT_X_AXIS_STEP.doubleValue()))
            .andExpect(jsonPath("$.yAxisStep").value(DEFAULT_Y_AXIS_STEP.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSavedChart() throws Exception {
        // Get the savedChart
        restSavedChartMockMvc.perform(get("/api/saved-charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedChart() throws Exception {
        // Initialize the database
        savedChartService.save(savedChart);

        int databaseSizeBeforeUpdate = savedChartRepository.findAll().size();

        // Update the savedChart
        SavedChart updatedSavedChart = savedChartRepository.findById(savedChart.getId()).get();
        // Disconnect from session so that the updates on updatedSavedChart are not directly saved in db
        em.detach(updatedSavedChart);
        updatedSavedChart
            .chartId(UPDATED_CHART_ID)
            .userId(UPDATED_USER_ID)
            .itemId(UPDATED_ITEM_ID)
            .indicator(UPDATED_INDICATOR)
            .xAxisStep(UPDATED_X_AXIS_STEP)
            .yAxisStep(UPDATED_Y_AXIS_STEP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restSavedChartMockMvc.perform(put("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSavedChart)))
            .andExpect(status().isOk());

        // Validate the SavedChart in the database
        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeUpdate);
        SavedChart testSavedChart = savedChartList.get(savedChartList.size() - 1);
        assertThat(testSavedChart.getChartId()).isEqualTo(UPDATED_CHART_ID);
        assertThat(testSavedChart.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSavedChart.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testSavedChart.getIndicator()).isEqualTo(UPDATED_INDICATOR);
        assertThat(testSavedChart.getxAxisStep()).isEqualTo(UPDATED_X_AXIS_STEP);
        assertThat(testSavedChart.getyAxisStep()).isEqualTo(UPDATED_Y_AXIS_STEP);
        assertThat(testSavedChart.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSavedChart.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSavedChart() throws Exception {
        int databaseSizeBeforeUpdate = savedChartRepository.findAll().size();

        // Create the SavedChart

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSavedChartMockMvc.perform(put("/api/saved-charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedChart)))
            .andExpect(status().isBadRequest());

        // Validate the SavedChart in the database
        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSavedChart() throws Exception {
        // Initialize the database
        savedChartService.save(savedChart);

        int databaseSizeBeforeDelete = savedChartRepository.findAll().size();

        // Delete the savedChart
        restSavedChartMockMvc.perform(delete("/api/saved-charts/{id}", savedChart.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SavedChart> savedChartList = savedChartRepository.findAll();
        assertThat(savedChartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
