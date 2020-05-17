package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.YahoofinanceApp;
import com.burynyk.yahoofinance.domain.WorldIndex;
import com.burynyk.yahoofinance.repository.WorldIndexRepository;
import com.burynyk.yahoofinance.service.WorldIndexService;

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
 * Integration tests for the {@link WorldIndexResource} REST controller.
 */
@SpringBootTest(classes = YahoofinanceApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class WorldIndexResourceIT {

    private static final String DEFAULT_INDEX_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LAST_PRICE = 1D;
    private static final Double UPDATED_LAST_PRICE = 2D;

    private static final Double DEFAULT_CHANGE = 1D;
    private static final Double UPDATED_CHANGE = 2D;

    private static final Long DEFAULT_VOLUME = 1L;
    private static final Long UPDATED_VOLUME = 2L;

    @Autowired
    private WorldIndexRepository worldIndexRepository;

    @Autowired
    private WorldIndexService worldIndexService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorldIndexMockMvc;

    private WorldIndex worldIndex;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorldIndex createEntity(EntityManager em) {
        WorldIndex worldIndex = new WorldIndex()
            .indexName(DEFAULT_INDEX_NAME)
            .lastPrice(DEFAULT_LAST_PRICE)
            .change(DEFAULT_CHANGE)
            .volume(DEFAULT_VOLUME);
        return worldIndex;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorldIndex createUpdatedEntity(EntityManager em) {
        WorldIndex worldIndex = new WorldIndex()
            .indexName(UPDATED_INDEX_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .change(UPDATED_CHANGE)
            .volume(UPDATED_VOLUME);
        return worldIndex;
    }

    @BeforeEach
    public void initTest() {
        worldIndex = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorldIndex() throws Exception {
        int databaseSizeBeforeCreate = worldIndexRepository.findAll().size();

        // Create the WorldIndex
        restWorldIndexMockMvc.perform(post("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isCreated());

        // Validate the WorldIndex in the database
        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeCreate + 1);
        WorldIndex testWorldIndex = worldIndexList.get(worldIndexList.size() - 1);
        assertThat(testWorldIndex.getIndexName()).isEqualTo(DEFAULT_INDEX_NAME);
        assertThat(testWorldIndex.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testWorldIndex.getChange()).isEqualTo(DEFAULT_CHANGE);
        assertThat(testWorldIndex.getVolume()).isEqualTo(DEFAULT_VOLUME);
    }

    @Test
    @Transactional
    public void createWorldIndexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = worldIndexRepository.findAll().size();

        // Create the WorldIndex with an existing ID
        worldIndex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorldIndexMockMvc.perform(post("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isBadRequest());

        // Validate the WorldIndex in the database
        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIndexNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = worldIndexRepository.findAll().size();
        // set the field null
        worldIndex.setIndexName(null);

        // Create the WorldIndex, which fails.

        restWorldIndexMockMvc.perform(post("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isBadRequest());

        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = worldIndexRepository.findAll().size();
        // set the field null
        worldIndex.setLastPrice(null);

        // Create the WorldIndex, which fails.

        restWorldIndexMockMvc.perform(post("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isBadRequest());

        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = worldIndexRepository.findAll().size();
        // set the field null
        worldIndex.setChange(null);

        // Create the WorldIndex, which fails.

        restWorldIndexMockMvc.perform(post("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isBadRequest());

        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVolumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = worldIndexRepository.findAll().size();
        // set the field null
        worldIndex.setVolume(null);

        // Create the WorldIndex, which fails.

        restWorldIndexMockMvc.perform(post("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isBadRequest());

        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorldIndices() throws Exception {
        // Initialize the database
        worldIndexRepository.saveAndFlush(worldIndex);

        // Get all the worldIndexList
        restWorldIndexMockMvc.perform(get("/api/world-indices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worldIndex.getId().intValue())))
            .andExpect(jsonPath("$.[*].indexName").value(hasItem(DEFAULT_INDEX_NAME)))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].change").value(hasItem(DEFAULT_CHANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.intValue())));
    }
    
    @Test
    @Transactional
    public void getWorldIndex() throws Exception {
        // Initialize the database
        worldIndexRepository.saveAndFlush(worldIndex);

        // Get the worldIndex
        restWorldIndexMockMvc.perform(get("/api/world-indices/{id}", worldIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(worldIndex.getId().intValue()))
            .andExpect(jsonPath("$.indexName").value(DEFAULT_INDEX_NAME))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE.doubleValue()))
            .andExpect(jsonPath("$.change").value(DEFAULT_CHANGE.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorldIndex() throws Exception {
        // Get the worldIndex
        restWorldIndexMockMvc.perform(get("/api/world-indices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorldIndex() throws Exception {
        // Initialize the database
        worldIndexService.save(worldIndex);

        int databaseSizeBeforeUpdate = worldIndexRepository.findAll().size();

        // Update the worldIndex
        WorldIndex updatedWorldIndex = worldIndexRepository.findById(worldIndex.getId()).get();
        // Disconnect from session so that the updates on updatedWorldIndex are not directly saved in db
        em.detach(updatedWorldIndex);
        updatedWorldIndex
            .indexName(UPDATED_INDEX_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .change(UPDATED_CHANGE)
            .volume(UPDATED_VOLUME);

        restWorldIndexMockMvc.perform(put("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorldIndex)))
            .andExpect(status().isOk());

        // Validate the WorldIndex in the database
        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeUpdate);
        WorldIndex testWorldIndex = worldIndexList.get(worldIndexList.size() - 1);
        assertThat(testWorldIndex.getIndexName()).isEqualTo(UPDATED_INDEX_NAME);
        assertThat(testWorldIndex.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testWorldIndex.getChange()).isEqualTo(UPDATED_CHANGE);
        assertThat(testWorldIndex.getVolume()).isEqualTo(UPDATED_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingWorldIndex() throws Exception {
        int databaseSizeBeforeUpdate = worldIndexRepository.findAll().size();

        // Create the WorldIndex

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorldIndexMockMvc.perform(put("/api/world-indices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worldIndex)))
            .andExpect(status().isBadRequest());

        // Validate the WorldIndex in the database
        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorldIndex() throws Exception {
        // Initialize the database
        worldIndexService.save(worldIndex);

        int databaseSizeBeforeDelete = worldIndexRepository.findAll().size();

        // Delete the worldIndex
        restWorldIndexMockMvc.perform(delete("/api/world-indices/{id}", worldIndex.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorldIndex> worldIndexList = worldIndexRepository.findAll();
        assertThat(worldIndexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
