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
 * A TarifCeinture.
 */
@Entity
@Table(name = "tarif_ceinture")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifCeinture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ceinture", nullable = false)
    private String ceinture;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Integer montant;

    @OneToMany(mappedBy = "tarifceinture")
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

    public String getCeinture() {
        return ceinture;
    }

    public TarifCeinture ceinture(String ceinture) {
        this.ceinture = ceinture;
        return this;
    }

    public void setCeinture(String ceinture) {
        this.ceinture = ceinture;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public TarifCeinture dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Integer getMontant() {
        return montant;
    }

    public TarifCeinture montant(Integer montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public Set<Licence> getLicences() {
        return licences;
    }

    public TarifCeinture licences(Set<Licence> licences) {
        this.licences = licences;
        return this;
    }

    public TarifCeinture addLicence(Licence licence) {
        this.licences.add(licence);
        licence.setTarifceinture(this);
        return this;
    }

    public TarifCeinture removeLicence(Licence licence) {
        this.licences.remove(licence);
        licence.setTarifceinture(null);
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
        TarifCeinture tarifCeinture = (TarifCeinture) o;
        if (tarifCeinture.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarifCeinture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TarifCeinture{" +
            "id=" + getId() +
            ", ceinture='" + getCeinture() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
