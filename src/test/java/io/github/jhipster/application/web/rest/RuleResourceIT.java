package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AllocationApp;
import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.repository.RuleRepository;
import io.github.jhipster.application.service.RuleService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RuleResource} REST controller.
 */
@SpringBootTest(classes = AllocationApp.class)
public class RuleResourceIT {

    private static final String DEFAULT_RULE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RULE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_RULE_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_CONTAINER = 1;
    private static final Integer UPDATED_MAX_CONTAINER = 2;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRuleMockMvc;

    private Rule rule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RuleResource ruleResource = new RuleResource(ruleService);
        this.restRuleMockMvc = MockMvcBuilders.standaloneSetup(ruleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createEntity(EntityManager em) {
        Rule rule = new Rule()
            .ruleId(DEFAULT_RULE_ID)
            .ruleDescription(DEFAULT_RULE_DESCRIPTION)
            .maxContainer(DEFAULT_MAX_CONTAINER);
        return rule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createUpdatedEntity(EntityManager em) {
        Rule rule = new Rule()
            .ruleId(UPDATED_RULE_ID)
            .ruleDescription(UPDATED_RULE_DESCRIPTION)
            .maxContainer(UPDATED_MAX_CONTAINER);
        return rule;
    }

    @BeforeEach
    public void initTest() {
        rule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRule() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule
        restRuleMockMvc.perform(post("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isCreated());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate + 1);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getRuleId()).isEqualTo(DEFAULT_RULE_ID);
        assertThat(testRule.getRuleDescription()).isEqualTo(DEFAULT_RULE_DESCRIPTION);
        assertThat(testRule.getMaxContainer()).isEqualTo(DEFAULT_MAX_CONTAINER);
    }

    @Test
    @Transactional
    public void createRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule with an existing ID
        rule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleMockMvc.perform(post("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRules() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList
        restRuleMockMvc.perform(get("/api/rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleId").value(hasItem(DEFAULT_RULE_ID.toString())))
            .andExpect(jsonPath("$.[*].ruleDescription").value(hasItem(DEFAULT_RULE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].maxContainer").value(hasItem(DEFAULT_MAX_CONTAINER)));
    }
    
    @Test
    @Transactional
    public void getRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", rule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rule.getId().intValue()))
            .andExpect(jsonPath("$.ruleId").value(DEFAULT_RULE_ID.toString()))
            .andExpect(jsonPath("$.ruleDescription").value(DEFAULT_RULE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.maxContainer").value(DEFAULT_MAX_CONTAINER));
    }

    @Test
    @Transactional
    public void getNonExistingRule() throws Exception {
        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRule() throws Exception {
        // Initialize the database
        ruleService.save(rule);

        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Update the rule
        Rule updatedRule = ruleRepository.findById(rule.getId()).get();
        // Disconnect from session so that the updates on updatedRule are not directly saved in db
        em.detach(updatedRule);
        updatedRule
            .ruleId(UPDATED_RULE_ID)
            .ruleDescription(UPDATED_RULE_DESCRIPTION)
            .maxContainer(UPDATED_MAX_CONTAINER);

        restRuleMockMvc.perform(put("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRule)))
            .andExpect(status().isOk());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getRuleId()).isEqualTo(UPDATED_RULE_ID);
        assertThat(testRule.getRuleDescription()).isEqualTo(UPDATED_RULE_DESCRIPTION);
        assertThat(testRule.getMaxContainer()).isEqualTo(UPDATED_MAX_CONTAINER);
    }

    @Test
    @Transactional
    public void updateNonExistingRule() throws Exception {
        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Create the Rule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleMockMvc.perform(put("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRule() throws Exception {
        // Initialize the database
        ruleService.save(rule);

        int databaseSizeBeforeDelete = ruleRepository.findAll().size();

        // Delete the rule
        restRuleMockMvc.perform(delete("/api/rules/{id}", rule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rule.class);
        Rule rule1 = new Rule();
        rule1.setId(1L);
        Rule rule2 = new Rule();
        rule2.setId(rule1.getId());
        assertThat(rule1).isEqualTo(rule2);
        rule2.setId(2L);
        assertThat(rule1).isNotEqualTo(rule2);
        rule1.setId(null);
        assertThat(rule1).isNotEqualTo(rule2);
    }
}
