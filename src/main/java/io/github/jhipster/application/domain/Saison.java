package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Saison.
 */
@Entity
@Table(name = "saison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Saison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 4)
    @Column(name = "saison", nullable = false)
    private Integer saison;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "saison")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Licence> licences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSaison() {
        return saison;
    }

    public Saison saison(Integer saison) {
        this.saison = saison;
        return this;
    }

    public void setSaison(Integer saison) {
        this.saison = saison;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Saison dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Set<Licence> getLicences() {
        return licences;
    }

    public Saison licences(Set<Licence> licences) {
        this.licences = licences;
        return this;
    }

    public Saison addLicence(Licence licence) {
        this.licences.add(licence);
        licence.setSaison(this);
        return this;
    }

    public Saison removeLicence(Licence licence) {
        this.licences.remove(licence);
        licence.setSaison(null);
        return this;
    }

    public void setLicences(Set<Licence> licences) {
        this.licences = licences;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Saison saison = (Saison) o;
        if (saison.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saison.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Saison{" +
            "id=" + getId() +
            ", saison=" + getSaison() +
            ", dateCreation='" + getDateCreation() + "'" +
            "}";
    }
}
