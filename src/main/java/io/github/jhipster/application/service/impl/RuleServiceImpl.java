package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RuleService;
import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.repository.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Rule}.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {

    private final Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);

    private final RuleRepository ruleRepository;

    public RuleServiceImpl(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Save a rule.
     *
     * @param rule the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Rule save(Rule rule) {
        log.debug("Request to save Rule : {}", rule);
        return ruleRepository.save(rule);
    }

    /**
     * Get all the rules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rule> findAll() {
        log.debug("Request to get all Rules");
        return ruleRepository.findAll();
    }


    /**
     * Get one rule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rule> findOne(Long id) {
        log.debug("Request to get Rule : {}", id);
        return ruleRepository.findById(id);
    }

    /**
     * Delete the rule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rule : {}", id);
        ruleRepository.deleteById(id);
    }
}
