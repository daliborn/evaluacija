package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A StrucnoUsavrsavanje.
 */
@Entity
@Table(name = "strucno_usavrsavanje")
public class StrucnoUsavrsavanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "pocetak_usavrsavanja")
    private ZonedDateTime pocetakUsavrsavanja;

    @Column(name = "kraj_usavrsavanja")
    private ZonedDateTime krajUsavrsavanja;

    @ManyToOne
    @JsonIgnoreProperties("strucnoUsavrsavanjes")
    private Aktivnosti aktivnosti;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public StrucnoUsavrsavanje naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ZonedDateTime getPocetakUsavrsavanja() {
        return pocetakUsavrsavanja;
    }

    public StrucnoUsavrsavanje pocetakUsavrsavanja(ZonedDateTime pocetakUsavrsavanja) {
        this.pocetakUsavrsavanja = pocetakUsavrsavanja;
        return this;
    }

    public void setPocetakUsavrsavanja(ZonedDateTime pocetakUsavrsavanja) {
        this.pocetakUsavrsavanja = pocetakUsavrsavanja;
    }

    public ZonedDateTime getKrajUsavrsavanja() {
        return krajUsavrsavanja;
    }

    public StrucnoUsavrsavanje krajUsavrsavanja(ZonedDateTime krajUsavrsavanja) {
        this.krajUsavrsavanja = krajUsavrsavanja;
        return this;
    }

    public void setKrajUsavrsavanja(ZonedDateTime krajUsavrsavanja) {
        this.krajUsavrsavanja = krajUsavrsavanja;
    }

    public Aktivnosti getAktivnosti() {
        return aktivnosti;
    }

    public StrucnoUsavrsavanje aktivnosti(Aktivnosti aktivnosti) {
        this.aktivnosti = aktivnosti;
        return this;
    }

    public void setAktivnosti(Aktivnosti aktivnosti) {
        this.aktivnosti = aktivnosti;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrucnoUsavrsavanje)) {
            return false;
        }
        return id != null && id.equals(((StrucnoUsavrsavanje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StrucnoUsavrsavanje{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", pocetakUsavrsavanja='" + getPocetakUsavrsavanja() + "'" +
            ", krajUsavrsavanja='" + getKrajUsavrsavanja() + "'" +
            "}";
    }
}
