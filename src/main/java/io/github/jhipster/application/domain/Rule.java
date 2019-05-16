package io.github.jhipster.application.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Rule.
 */
@Entity
@Table(name = "rule")
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_id")
    private String ruleId;

    @Column(name = "rule_description")
    private String ruleDescription;

    @Column(name = "max_container")
    private Integer maxContainer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public Rule ruleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public Rule ruleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
        return this;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public Integer getMaxContainer() {
        return maxContainer;
    }

    public Rule maxContainer(Integer maxContainer) {
        this.maxContainer = maxContainer;
        return this;
    }

    public void setMaxContainer(Integer maxContainer) {
        this.maxContainer = maxContainer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rule)) {
            return false;
        }
        return id != null && id.equals(((Rule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", ruleId='" + getRuleId() + "'" +
            ", ruleDescription='" + getRuleDescription() + "'" +
            ", maxContainer=" + getMaxContainer() +
            "}";
    }
}
