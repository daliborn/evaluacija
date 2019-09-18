package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Kandidat.
 */
@Entity
@Table(name = "kandidat")
public class Kandidat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ime", nullable = false)
    private String ime;

    @NotNull
    @Column(name = "prezime", nullable = false)
    private String prezime;

    @Column(name = "jmbg")
    private String jmbg;

    @OneToOne(mappedBy = "oblasti")
    @JsonIgnore
    private MentorskiRad diplome;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public Kandidat ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Kandidat prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public Kandidat jmbg(String jmbg) {
        this.jmbg = jmbg;
        return this;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public MentorskiRad getDiplome() {
        return diplome;
    }

    public Kandidat diplome(MentorskiRad mentorskiRad) {
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
        if (!(o instanceof Kandidat)) {
            return false;
        }
        return id != null && id.equals(((Kandidat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Kandidat{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", jmbg='" + getJmbg() + "'" +
            "}";
    }
}
