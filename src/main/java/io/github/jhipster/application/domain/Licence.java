package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.TypeLicence;

/**
 * A Licence.
 */
@Entity
@Table(name = "licence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Licence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_licence", nullable = false)
    private TypeLicence typeLicence;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @NotNull
    @Lob
    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @Column(name = "photo_content_type", nullable = false)
    private String photoContentType;

    @NotNull
    @Lob
    @Column(name = "certificat_medical", nullable = false)
    private byte[] certificatMedical;

    @Column(name = "certificat_medical_content_type", nullable = false)
    private String certificatMedicalContentType;

    @Column(name = "licence_anterieur")
    private Long licenceAnterieur;

    @ManyToOne(optional = false)
    @NotNull
    private Athlete athlete;

    @ManyToOne(optional = false)
    @NotNull
    private TarifCeinture tarifceinture;

    @ManyToOne(optional = false)
    @NotNull
    private Saison saison;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeLicence getTypeLicence() {
        return typeLicence;
    }

    public Licence typeLicence(TypeLicence typeLicence) {
        this.typeLicence = typeLicence;
        return this;
    }

    public void setTypeLicence(TypeLicence typeLicence) {
        this.typeLicence = typeLicence;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Licence dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Licence photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Licence photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public byte[] getCertificatMedical() {
        return certificatMedical;
    }

    public Licence certificatMedical(byte[] certificatMedical) {
        this.certificatMedical = certificatMedical;
        return this;
    }

    public void setCertificatMedical(byte[] certificatMedical) {
        this.certificatMedical = certificatMedical;
    }

    public String getCertificatMedicalContentType() {
        return certificatMedicalContentType;
    }

    public Licence certificatMedicalContentType(String certificatMedicalContentType) {
        this.certificatMedicalContentType = certificatMedicalContentType;
        return this;
    }

    public void setCertificatMedicalContentType(String certificatMedicalContentType) {
        this.certificatMedicalContentType = certificatMedicalContentType;
    }

    public Long getLicenceAnterieur() {
        return licenceAnterieur;
    }

    public Licence licenceAnterieur(Long licenceAnterieur) {
        this.licenceAnterieur = licenceAnterieur;
        return this;
    }

    public void setLicenceAnterieur(Long licenceAnterieur) {
        this.licenceAnterieur = licenceAnterieur;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Licence athlete(Athlete athlete) {
        this.athlete = athlete;
        return this;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public TarifCeinture getTarifceinture() {
        return tarifceinture;
    }

    public Licence tarifceinture(TarifCeinture tarifCeinture) {
        this.tarifceinture = tarifCeinture;
        return this;
    }

    public void setTarifceinture(TarifCeinture tarifCeinture) {
        this.tarifceinture = tarifCeinture;
    }

    public Saison getSaison() {
        return saison;
    }

    public Licence saison(Saison saison) {
        this.saison = saison;
        return this;
    }

    public void setSaison(Saison saison) {
        this.saison = saison;
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
        Licence licence = (Licence) o;
        if (licence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Licence{" +
            "id=" + getId() +
            ", typeLicence='" + getTypeLicence() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", certificatMedical='" + getCertificatMedical() + "'" +
            ", certificatMedicalContentType='" + getCertificatMedicalContentType() + "'" +
            ", licenceAnterieur=" + getLicenceAnterieur() +
            "}";
    }
}
