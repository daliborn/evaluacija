package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A MentorskiRad.
 */
@Entity
@Table(name = "mentorski_rad")
public class MentorskiRad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "datum_pocetka_mentorskog_rada")
    private ZonedDateTime datumPocetkaMentorskogRada;

    @Column(name = "datum_zavrsetka_mentorskog_rada")
    private ZonedDateTime datumZavrsetkaMentorskogRada;

    @OneToOne
    @JoinColumn(unique = true)
    private Kandidat oblasti;

    @OneToOne
    @JoinColumn(unique = true)
    private VrstaMentorstva vrstaMentorstva;

    @ManyToMany(mappedBy = "mentorskiRads")
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

    public MentorskiRad naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ZonedDateTime getDatumPocetkaMentorskogRada() {
        return datumPocetkaMentorskogRada;
    }

    public MentorskiRad datumPocetkaMentorskogRada(ZonedDateTime datumPocetkaMentorskogRada) {
        this.datumPocetkaMentorskogRada = datumPocetkaMentorskogRada;
        return this;
    }

    public void setDatumPocetkaMentorskogRada(ZonedDateTime datumPocetkaMentorskogRada) {
        this.datumPocetkaMentorskogRada = datumPocetkaMentorskogRada;
    }

    public ZonedDateTime getDatumZavrsetkaMentorskogRada() {
        return datumZavrsetkaMentorskogRada;
    }

    public MentorskiRad datumZavrsetkaMentorskogRada(ZonedDateTime datumZavrsetkaMentorskogRada) {
        this.datumZavrsetkaMentorskogRada = datumZavrsetkaMentorskogRada;
        return this;
    }

    public void setDatumZavrsetkaMentorskogRada(ZonedDateTime datumZavrsetkaMentorskogRada) {
        this.datumZavrsetkaMentorskogRada = datumZavrsetkaMentorskogRada;
    }

    public Kandidat getOblasti() {
        return oblasti;
    }

    public MentorskiRad oblasti(Kandidat kandidat) {
        this.oblasti = kandidat;
        return this;
    }

    public void setOblasti(Kandidat kandidat) {
        this.oblasti = kandidat;
    }

    public VrstaMentorstva getVrstaMentorstva() {
        return vrstaMentorstva;
    }

    public MentorskiRad vrstaMentorstva(VrstaMentorstva vrstaMentorstva) {
        this.vrstaMentorstva = vrstaMentorstva;
        return this;
    }

    public void setVrstaMentorstva(VrstaMentorstva vrstaMentorstva) {
        this.vrstaMentorstva = vrstaMentorstva;
    }

    public Set<Nastavnik> getNastavnicis() {
        return nastavnicis;
    }

    public MentorskiRad nastavnicis(Set<Nastavnik> nastavniks) {
        this.nastavnicis = nastavniks;
        return this;
    }

    public MentorskiRad addNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.add(nastavnik);
        nastavnik.getMentorskiRads().add(this);
        return this;
    }

    public MentorskiRad removeNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.remove(nastavnik);
        nastavnik.getMentorskiRads().remove(this);
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
        if (!(o instanceof MentorskiRad)) {
            return false;
        }
        return id != null && id.equals(((MentorskiRad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MentorskiRad{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", datumPocetkaMentorskogRada='" + getDatumPocetkaMentorskogRada() + "'" +
            ", datumZavrsetkaMentorskogRada='" + getDatumZavrsetkaMentorskogRada() + "'" +
            "}";
    }
}
