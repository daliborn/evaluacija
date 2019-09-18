package eu.apeiron.dalibor.evaulacija.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Institucija.
 */
@Entity
@Table(name = "institucija")
public class Institucija implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sifra")
    private String sifra;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "mjesto")
    private String mjesto;

    @OneToMany(mappedBy = "institucija")
    private Set<ZavrsniRad> zavrsniRadovis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public Institucija sifra(String sifra) {
        this.sifra = sifra;
        return this;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public Institucija naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMjesto() {
        return mjesto;
    }

    public Institucija mjesto(String mjesto) {
        this.mjesto = mjesto;
        return this;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public Set<ZavrsniRad> getZavrsniRadovis() {
        return zavrsniRadovis;
    }

    public Institucija zavrsniRadovis(Set<ZavrsniRad> zavrsniRads) {
        this.zavrsniRadovis = zavrsniRads;
        return this;
    }

    public Institucija addZavrsniRadovi(ZavrsniRad zavrsniRad) {
        this.zavrsniRadovis.add(zavrsniRad);
        zavrsniRad.setInstitucija(this);
        return this;
    }

    public Institucija removeZavrsniRadovi(ZavrsniRad zavrsniRad) {
        this.zavrsniRadovis.remove(zavrsniRad);
        zavrsniRad.setInstitucija(null);
        return this;
    }

    public void setZavrsniRadovis(Set<ZavrsniRad> zavrsniRads) {
        this.zavrsniRadovis = zavrsniRads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Institucija)) {
            return false;
        }
        return id != null && id.equals(((Institucija) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Institucija{" +
            "id=" + getId() +
            ", sifra='" + getSifra() + "'" +
            ", naziv='" + getNaziv() + "'" +
            ", mjesto='" + getMjesto() + "'" +
            "}";
    }
}
