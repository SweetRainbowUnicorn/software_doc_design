package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.YahoofinanceApp;
import com.burynyk.yahoofinance.domain.Chart;
import com.burynyk.yahoofinance.repository.ChartRepository;
import com.burynyk.yahoofinance.service.ChartService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.burynyk.yahoofinance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChartResource} REST controller.
 */
@SpringBootTest(classes = YahoofinanceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChartResourceIT {

    private static final String DEFAULT_CHART_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHART_NAME = "BBBBBBBBBB";

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
    private ChartRepository chartRepository;

    @Mock
    private ChartRepository chartRepositoryMock;

    @Mock
    private ChartService chartServiceMock;

    @Autowired
    private ChartService chartService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChartMockMvc;

    private Chart chart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chart createEntity(EntityManager em) {
        Chart chart = new Chart()
            .chartName(DEFAULT_CHART_NAME)
            .itemId(DEFAULT_ITEM_ID)
            .indicator(DEFAULT_INDICATOR)
            .xAxisStep(DEFAULT_X_AXIS_STEP)
            .yAxisStep(DEFAULT_Y_AXIS_STEP)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return chart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chart createUpdatedEntity(EntityManager em) {
        Chart chart = new Chart()
            .chartName(UPDATED_CHART_NAME)
            .itemId(UPDATED_ITEM_ID)
            .indicator(UPDATED_INDICATOR)
            .xAxisStep(UPDATED_X_AXIS_STEP)
            .yAxisStep(UPDATED_Y_AXIS_STEP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return chart;
    }

    @BeforeEach
    public void initTest() {
        chart = createEntity(em);
    }

    @Test
    @Transactional
    public void createChart() throws Exception {
        int databaseSizeBeforeCreate = chartRepository.findAll().size();

        // Create the Chart
        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isCreated());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeCreate + 1);
        Chart testChart = chartList.get(chartList.size() - 1);
        assertThat(testChart.getChartName()).isEqualTo(DEFAULT_CHART_NAME);
        assertThat(testChart.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testChart.getIndicator()).isEqualTo(DEFAULT_INDICATOR);
        assertThat(testChart.getxAxisStep()).isEqualTo(DEFAULT_X_AXIS_STEP);
        assertThat(testChart.getyAxisStep()).isEqualTo(DEFAULT_Y_AXIS_STEP);
        assertThat(testChart.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testChart.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chartRepository.findAll().size();

        // Create the Chart with an existing ID
        chart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChartNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setChartName(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setItemId(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndicatorIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setIndicator(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkxAxisStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setxAxisStep(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkyAxisStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setyAxisStep(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setStartDate(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartRepository.findAll().size();
        // set the field null
        chart.setEndDate(null);

        // Create the Chart, which fails.

        restChartMockMvc.perform(post("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCharts() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList
        restChartMockMvc.perform(get("/api/charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chart.getId().intValue())))
            .andExpect(jsonPath("$.[*].chartName").value(hasItem(DEFAULT_CHART_NAME)))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].indicator").value(hasItem(DEFAULT_INDICATOR.intValue())))
            .andExpect(jsonPath("$.[*].xAxisStep").value(hasItem(DEFAULT_X_AXIS_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].yAxisStep").value(hasItem(DEFAULT_Y_AXIS_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllChartsWithEagerRelationshipsIsEnabled() throws Exception {
        when(chartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChartMockMvc.perform(get("/api/charts?eagerload=true"))
            .andExpect(status().isOk());

        verify(chartServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChartsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(chartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChartMockMvc.perform(get("/api/charts?eagerload=true"))
            .andExpect(status().isOk());

        verify(chartServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChart() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get the chart
        restChartMockMvc.perform(get("/api/charts/{id}", chart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chart.getId().intValue()))
            .andExpect(jsonPath("$.chartName").value(DEFAULT_CHART_NAME))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.indicator").value(DEFAULT_INDICATOR.intValue()))
            .andExpect(jsonPath("$.xAxisStep").value(DEFAULT_X_AXIS_STEP.doubleValue()))
            .andExpect(jsonPath("$.yAxisStep").value(DEFAULT_Y_AXIS_STEP.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingChart() throws Exception {
        // Get the chart
        restChartMockMvc.perform(get("/api/charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChart() throws Exception {
        // Initialize the database
        chartService.save(chart);

        int databaseSizeBeforeUpdate = chartRepository.findAll().size();

        // Update the chart
        Chart updatedChart = chartRepository.findById(chart.getId()).get();
        // Disconnect from session so that the updates on updatedChart are not directly saved in db
        em.detach(updatedChart);
        updatedChart
            .chartName(UPDATED_CHART_NAME)
            .itemId(UPDATED_ITEM_ID)
            .indicator(UPDATED_INDICATOR)
            .xAxisStep(UPDATED_X_AXIS_STEP)
            .yAxisStep(UPDATED_Y_AXIS_STEP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restChartMockMvc.perform(put("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChart)))
            .andExpect(status().isOk());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeUpdate);
        Chart testChart = chartList.get(chartList.size() - 1);
        assertThat(testChart.getChartName()).isEqualTo(UPDATED_CHART_NAME);
        assertThat(testChart.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testChart.getIndicator()).isEqualTo(UPDATED_INDICATOR);
        assertThat(testChart.getxAxisStep()).isEqualTo(UPDATED_X_AXIS_STEP);
        assertThat(testChart.getyAxisStep()).isEqualTo(UPDATED_Y_AXIS_STEP);
        assertThat(testChart.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testChart.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingChart() throws Exception {
        int databaseSizeBeforeUpdate = chartRepository.findAll().size();

        // Create the Chart

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChartMockMvc.perform(put("/api/charts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChart() throws Exception {
        // Initialize the database
        chartService.save(chart);

        int databaseSizeBeforeDelete = chartRepository.findAll().size();

        // Delete the chart
        restChartMockMvc.perform(delete("/api/charts/{id}", chart.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
