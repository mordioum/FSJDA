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
 * A Ligue.
 */
@Entity
@Table(name = "ligue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ligue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "president", nullable = false)
    private String president;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "ligue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DojoClub> dojoclubs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Discipline discipline;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Ligue name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresident() {
        return president;
    }

    public Ligue president(String president) {
        this.president = president;
        return this;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Ligue dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public Ligue description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public Ligue adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Ligue telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Ligue email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<DojoClub> getDojoclubs() {
        return dojoclubs;
    }

    public Ligue dojoclubs(Set<DojoClub> dojoClubs) {
        this.dojoclubs = dojoClubs;
        return this;
    }

    public Ligue addDojoclub(DojoClub dojoClub) {
        this.dojoclubs.add(dojoClub);
        dojoClub.setLigue(this);
        return this;
    }

    public Ligue removeDojoclub(DojoClub dojoClub) {
        this.dojoclubs.remove(dojoClub);
        dojoClub.setLigue(null);
        return this;
    }

    public void setDojoclubs(Set<DojoClub> dojoClubs) {
        this.dojoclubs = dojoClubs;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public Ligue discipline(Discipline discipline) {
        this.discipline = discipline;
        return this;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
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
        Ligue ligue = (Ligue) o;
        if (ligue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ligue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ligue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", president='" + getPresident() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", description='" + getDescription() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
