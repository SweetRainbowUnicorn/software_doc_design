package com.burynyk.yahoofinance.web.rest;

import com.burynyk.yahoofinance.YahoofinanceApp;
import com.burynyk.yahoofinance.domain.YahooUser;
import com.burynyk.yahoofinance.repository.YahooUserRepository;
import com.burynyk.yahoofinance.service.YahooUserService;

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
 * Integration tests for the {@link YahooUserResource} REST controller.
 */
@SpringBootTest(classes = YahoofinanceApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class YahooUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;

    private static final String DEFAULT_INTEREST = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST = "BBBBBBBBBB";

    @Autowired
    private YahooUserRepository yahooUserRepository;

    @Autowired
    private YahooUserService yahooUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restYahooUserMockMvc;

    private YahooUser yahooUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YahooUser createEntity(EntityManager em) {
        YahooUser yahooUser = new YahooUser()
            .username(DEFAULT_USERNAME)
            .age(DEFAULT_AGE)
            .interest(DEFAULT_INTEREST);
        return yahooUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YahooUser createUpdatedEntity(EntityManager em) {
        YahooUser yahooUser = new YahooUser()
            .username(UPDATED_USERNAME)
            .age(UPDATED_AGE)
            .interest(UPDATED_INTEREST);
        return yahooUser;
    }

    @BeforeEach
    public void initTest() {
        yahooUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createYahooUser() throws Exception {
        int databaseSizeBeforeCreate = yahooUserRepository.findAll().size();

        // Create the YahooUser
        restYahooUserMockMvc.perform(post("/api/yahoo-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yahooUser)))
            .andExpect(status().isCreated());

        // Validate the YahooUser in the database
        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeCreate + 1);
        YahooUser testYahooUser = yahooUserList.get(yahooUserList.size() - 1);
        assertThat(testYahooUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testYahooUser.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testYahooUser.getInterest()).isEqualTo(DEFAULT_INTEREST);
    }

    @Test
    @Transactional
    public void createYahooUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = yahooUserRepository.findAll().size();

        // Create the YahooUser with an existing ID
        yahooUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restYahooUserMockMvc.perform(post("/api/yahoo-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yahooUser)))
            .andExpect(status().isBadRequest());

        // Validate the YahooUser in the database
        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = yahooUserRepository.findAll().size();
        // set the field null
        yahooUser.setUsername(null);

        // Create the YahooUser, which fails.

        restYahooUserMockMvc.perform(post("/api/yahoo-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yahooUser)))
            .andExpect(status().isBadRequest());

        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = yahooUserRepository.findAll().size();
        // set the field null
        yahooUser.setAge(null);

        // Create the YahooUser, which fails.

        restYahooUserMockMvc.perform(post("/api/yahoo-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yahooUser)))
            .andExpect(status().isBadRequest());

        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYahooUsers() throws Exception {
        // Initialize the database
        yahooUserRepository.saveAndFlush(yahooUser);

        // Get all the yahooUserList
        restYahooUserMockMvc.perform(get("/api/yahoo-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(yahooUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(DEFAULT_INTEREST)));
    }
    
    @Test
    @Transactional
    public void getYahooUser() throws Exception {
        // Initialize the database
        yahooUserRepository.saveAndFlush(yahooUser);

        // Get the yahooUser
        restYahooUserMockMvc.perform(get("/api/yahoo-users/{id}", yahooUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(yahooUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.interest").value(DEFAULT_INTEREST));
    }

    @Test
    @Transactional
    public void getNonExistingYahooUser() throws Exception {
        // Get the yahooUser
        restYahooUserMockMvc.perform(get("/api/yahoo-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYahooUser() throws Exception {
        // Initialize the database
        yahooUserService.save(yahooUser);

        int databaseSizeBeforeUpdate = yahooUserRepository.findAll().size();

        // Update the yahooUser
        YahooUser updatedYahooUser = yahooUserRepository.findById(yahooUser.getId()).get();
        // Disconnect from session so that the updates on updatedYahooUser are not directly saved in db
        em.detach(updatedYahooUser);
        updatedYahooUser
            .username(UPDATED_USERNAME)
            .age(UPDATED_AGE)
            .interest(UPDATED_INTEREST);

        restYahooUserMockMvc.perform(put("/api/yahoo-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedYahooUser)))
            .andExpect(status().isOk());

        // Validate the YahooUser in the database
        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeUpdate);
        YahooUser testYahooUser = yahooUserList.get(yahooUserList.size() - 1);
        assertThat(testYahooUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testYahooUser.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testYahooUser.getInterest()).isEqualTo(UPDATED_INTEREST);
    }

    @Test
    @Transactional
    public void updateNonExistingYahooUser() throws Exception {
        int databaseSizeBeforeUpdate = yahooUserRepository.findAll().size();

        // Create the YahooUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYahooUserMockMvc.perform(put("/api/yahoo-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yahooUser)))
            .andExpect(status().isBadRequest());

        // Validate the YahooUser in the database
        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteYahooUser() throws Exception {
        // Initialize the database
        yahooUserService.save(yahooUser);

        int databaseSizeBeforeDelete = yahooUserRepository.findAll().size();

        // Delete the yahooUser
        restYahooUserMockMvc.perform(delete("/api/yahoo-users/{id}", yahooUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<YahooUser> yahooUserList = yahooUserRepository.findAll();
        assertThat(yahooUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
