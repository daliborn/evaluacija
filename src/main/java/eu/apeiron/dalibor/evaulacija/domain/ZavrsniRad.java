package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A ZavrsniRad.
 */
@Entity
@Table(name = "zavrsni_rad")
public class ZavrsniRad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tip_studija", nullable = false)
    private String tipStudija;

    @Column(name = "mentor")
    private String mentor;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "datum_zavrsetka_rada")
    private ZonedDateTime datumZavrsetkaRada;

    @OneToMany(mappedBy = "zavrsniRadovi")
    private Set<NaucneOblasti> oblastis = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("zavrsniRadovis")
    private Institucija institucija;

    @ManyToMany(mappedBy = "zavrsniRadovis")
    @JsonIgnore
    private Set<Nastavnik> nastavnicis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipStudija() {
        return tipStudija;
    }

    public ZavrsniRad tipStudija(String tipStudija) {
        this.tipStudija = tipStudija;
        return this;
    }

    public void setTipStudija(String tipStudija) {
        this.tipStudija = tipStudija;
    }

    public String getMentor() {
        return mentor;
    }

    public ZavrsniRad mentor(String mentor) {
        this.mentor = mentor;
        return this;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getNaziv() {
        return naziv;
    }

    public ZavrsniRad naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ZonedDateTime getDatumZavrsetkaRada() {
        return datumZavrsetkaRada;
    }

    public ZavrsniRad datumZavrsetkaRada(ZonedDateTime datumZavrsetkaRada) {
        this.datumZavrsetkaRada = datumZavrsetkaRada;
        return this;
    }

    public void setDatumZavrsetkaRada(ZonedDateTime datumZavrsetkaRada) {
        this.datumZavrsetkaRada = datumZavrsetkaRada;
    }

    public Set<NaucneOblasti> getOblastis() {
        return oblastis;
    }

    public ZavrsniRad oblastis(Set<NaucneOblasti> naucneOblastis) {
        this.oblastis = naucneOblastis;
        return this;
    }

    public ZavrsniRad addOblasti(NaucneOblasti naucneOblasti) {
        this.oblastis.add(naucneOblasti);
        naucneOblasti.setZavrsniRadovi(this);
        return this;
    }

    public ZavrsniRad removeOblasti(NaucneOblasti naucneOblasti) {
        this.oblastis.remove(naucneOblasti);
        naucneOblasti.setZavrsniRadovi(null);
        return this;
    }

    public void setOblastis(Set<NaucneOblasti> naucneOblastis) {
        this.oblastis = naucneOblastis;
    }

    public Institucija getInstitucija() {
        return institucija;
    }

    public ZavrsniRad institucija(Institucija institucija) {
        this.institucija = institucija;
        return this;
    }

    public void setInstitucija(Institucija institucija) {
        this.institucija = institucija;
    }

    public Set<Nastavnik> getNastavnicis() {
        return nastavnicis;
    }

    public ZavrsniRad nastavnicis(Set<Nastavnik> nastavniks) {
        this.nastavnicis = nastavniks;
        return this;
    }

    public ZavrsniRad addNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.add(nastavnik);
        nastavnik.getZavrsniRadovis().add(this);
        return this;
    }

    public ZavrsniRad removeNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.remove(nastavnik);
        nastavnik.getZavrsniRadovis().remove(this);
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
        if (!(o instanceof ZavrsniRad)) {
            return false;
        }
        return id != null && id.equals(((ZavrsniRad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ZavrsniRad{" +
            "id=" + getId() +
            ", tipStudija='" + getTipStudija() + "'" +
            ", mentor='" + getMentor() + "'" +
            ", naziv='" + getNaziv() + "'" +
            ", datumZavrsetkaRada='" + getDatumZavrsetkaRada() + "'" +
            "}";
    }
}
