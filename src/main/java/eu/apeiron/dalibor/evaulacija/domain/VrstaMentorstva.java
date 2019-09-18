package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A VrstaMentorstva.
 */
@Entity
@Table(name = "vrsta_mentorstva")
public class VrstaMentorstva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tip", nullable = false)
    private String tip;

    @OneToOne(mappedBy = "vrstaMentorstva")
    @JsonIgnore
    private MentorskiRad diplome;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public VrstaMentorstva tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public MentorskiRad getDiplome() {
        return diplome;
    }

    public VrstaMentorstva diplome(MentorskiRad mentorskiRad) {
        this.diplome = mentorskiRad;
        return this;
    }

    public void setDiplome(MentorskiRad mentorskiRad) {
        this.diplome = mentorskiRad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VrstaMentorstva)) {
            return false;
        }
        return id != null && id.equals(((VrstaMentorstva) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VrstaMentorstva{" +
            "id=" + getId() +
            ", tip='" + getTip() + "'" +
            "}";
    }
}
