package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projekat.
 */
@Entity
@Table(name = "projekat")
public class Projekat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @NotNull
    @Column(name = "vrsta_projekta", nullable = false)
    private String vrstaProjekta;

    @Column(name = "datum_zavrsetka_projekta")
    private ZonedDateTime datumZavrsetkaProjekta;

    @Column(name = "datum_pocetka_projekta")
    private ZonedDateTime datumPocetkaProjekta;

    @ManyToMany(mappedBy = "projektis")
    @JsonIgnore
    private Set<Nastavnik> nastavnicis = new HashSet<>();

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

    public Projekat naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVrstaProjekta() {
        return vrstaProjekta;
    }

    public Projekat vrstaProjekta(String vrstaProjekta) {
        this.vrstaProjekta = vrstaProjekta;
        return this;
    }

    public void setVrstaProjekta(String vrstaProjekta) {
        this.vrstaProjekta = vrstaProjekta;
    }

    public ZonedDateTime getDatumZavrsetkaProjekta() {
        return datumZavrsetkaProjekta;
    }

    public Projekat datumZavrsetkaProjekta(ZonedDateTime datumZavrsetkaProjekta) {
        this.datumZavrsetkaProjekta = datumZavrsetkaProjekta;
        return this;
    }

    public void setDatumZavrsetkaProjekta(ZonedDateTime datumZavrsetkaProjekta) {
        this.datumZavrsetkaProjekta = datumZavrsetkaProjekta;
    }

    public ZonedDateTime getDatumPocetkaProjekta() {
        return datumPocetkaProjekta;
    }

    public Projekat datumPocetkaProjekta(ZonedDateTime datumPocetkaProjekta) {
        this.datumPocetkaProjekta = datumPocetkaProjekta;
        return this;
    }

    public void setDatumPocetkaProjekta(ZonedDateTime datumPocetkaProjekta) {
        this.datumPocetkaProjekta = datumPocetkaProjekta;
    }

    public Set<Nastavnik> getNastavnicis() {
        return nastavnicis;
    }

    public Projekat nastavnicis(Set<Nastavnik> nastavniks) {
        this.nastavnicis = nastavniks;
        return this;
    }

    public Projekat addNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.add(nastavnik);
        nastavnik.getProjektis().add(this);
        return this;
    }

    public Projekat removeNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.remove(nastavnik);
        nastavnik.getProjektis().remove(this);
        return this;
    }

    public void setNastavnicis(Set<Nastavnik> nastavniks) {
        this.nastavnicis = nastavniks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projekat)) {
            return false;
        }
        return id != null && id.equals(((Projekat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Projekat{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", vrstaProjekta='" + getVrstaProjekta() + "'" +
            ", datumZavrsetkaProjekta='" + getDatumZavrsetkaProjekta() + "'" +
            ", datumPocetkaProjekta='" + getDatumPocetkaProjekta() + "'" +
            "}";
    }
}
