package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aktivnosti.
 */
@Entity
@Table(name = "aktivnosti")
public class Aktivnosti implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "broj_citata")
    private Integer brojCitata;

    @Column(name = "broj_radova")
    private Integer brojRadova;

    @Column(name = "broj_domaci_projekata")
    private Integer brojDomaciProjekata;

    @Column(name = "broj_medjunarodnih_projekata")
    private Integer brojMedjunarodnihProjekata;

    @OneToMany(mappedBy = "aktivnosti")
    private Set<StrucnoUsavrsavanje> usavrsavanjas = new HashSet<>();

    @ManyToMany(mappedBy = "aktivnostis")
    @JsonIgnore
    private Set<Nastavnik> nastavnicis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBrojCitata() {
        return brojCitata;
    }

    public Aktivnosti brojCitata(Integer brojCitata) {
        this.brojCitata = brojCitata;
        return this;
    }

    public void setBrojCitata(Integer brojCitata) {
        this.brojCitata = brojCitata;
    }

    public Integer getBrojRadova() {
        return brojRadova;
    }

    public Aktivnosti brojRadova(Integer brojRadova) {
        this.brojRadova = brojRadova;
        return this;
    }

    public void setBrojRadova(Integer brojRadova) {
        this.brojRadova = brojRadova;
    }

    public Integer getBrojDomaciProjekata() {
        return brojDomaciProjekata;
    }

    public Aktivnosti brojDomaciProjekata(Integer brojDomaciProjekata) {
        this.brojDomaciProjekata = brojDomaciProjekata;
        return this;
    }

    public void setBrojDomaciProjekata(Integer brojDomaciProjekata) {
        this.brojDomaciProjekata = brojDomaciProjekata;
    }

    public Integer getBrojMedjunarodnihProjekata() {
        return brojMedjunarodnihProjekata;
    }

    public Aktivnosti brojMedjunarodnihProjekata(Integer brojMedjunarodnihProjekata) {
        this.brojMedjunarodnihProjekata = brojMedjunarodnihProjekata;
        return this;
    }

    public void setBrojMedjunarodnihProjekata(Integer brojMedjunarodnihProjekata) {
        this.brojMedjunarodnihProjekata = brojMedjunarodnihProjekata;
    }

    public Set<StrucnoUsavrsavanje> getUsavrsavanjas() {
        return usavrsavanjas;
    }

    public Aktivnosti usavrsavanjas(Set<StrucnoUsavrsavanje> strucnoUsavrsavanjes) {
        this.usavrsavanjas = strucnoUsavrsavanjes;
        return this;
    }

    public Aktivnosti addUsavrsavanja(StrucnoUsavrsavanje strucnoUsavrsavanje) {
        this.usavrsavanjas.add(strucnoUsavrsavanje);
        strucnoUsavrsavanje.setAktivnosti(this);
        return this;
    }

    public Aktivnosti removeUsavrsavanja(StrucnoUsavrsavanje strucnoUsavrsavanje) {
        this.usavrsavanjas.remove(strucnoUsavrsavanje);
        strucnoUsavrsavanje.setAktivnosti(null);
        return this;
    }

    public void setUsavrsavanjas(Set<StrucnoUsavrsavanje> strucnoUsavrsavanjes) {
        this.usavrsavanjas = strucnoUsavrsavanjes;
    }

    public Set<Nastavnik> getNastavnicis() {
        return nastavnicis;
    }

    public Aktivnosti nastavnicis(Set<Nastavnik> nastavniks) {
        this.nastavnicis = nastavniks;
        return this;
    }

    public Aktivnosti addNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.add(nastavnik);
        nastavnik.getAktivnostis().add(this);
        return this;
    }

    public Aktivnosti removeNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.remove(nastavnik);
        nastavnik.getAktivnostis().remove(this);
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
        if (!(o instanceof Aktivnosti)) {
            return false;
        }
        return id != null && id.equals(((Aktivnosti) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Aktivnosti{" +
            "id=" + getId() +
            ", brojCitata=" + getBrojCitata() +
            ", brojRadova=" + getBrojRadova() +
            ", brojDomaciProjekata=" + getBrojDomaciProjekata() +
            ", brojMedjunarodnihProjekata=" + getBrojMedjunarodnihProjekata() +
            "}";
    }
}
