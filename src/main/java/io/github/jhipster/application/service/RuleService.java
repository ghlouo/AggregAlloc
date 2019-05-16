package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Rule;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Rule}.
 */
public interface RuleService {

    /**
     * Save a rule.
     *
     * @param rule the entity to save.
     * @return the persisted entity.
     */
    Rule save(Rule rule);

    /**
     * Get all the rules.
     *
     * @return the list of entities.
     */
    List<Rule> findAll();


    /**
     * Get the "id" rule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rule> findOne(Long id);

    /**
     * Delete the "id" rule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
