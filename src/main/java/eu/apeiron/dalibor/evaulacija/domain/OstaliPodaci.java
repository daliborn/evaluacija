package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A OstaliPodaci.
 */
@Entity
@Table(name = "ostali_podaci")
public class OstaliPodaci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ostalo")
    private String ostalo;

    @Column(name = "datum")
    private ZonedDateTime datum;

    @ManyToMany(mappedBy = "ostaliPodacis")
    @JsonIgnore
    private Set<Nastavnik> nastavnicis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOstalo() {
        return ostalo;
    }

    public OstaliPodaci ostalo(String ostalo) {
        this.ostalo = ostalo;
        return this;
    }

    public void setOstalo(String ostalo) {
        this.ostalo = ostalo;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public OstaliPodaci datum(ZonedDateTime datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Set<Nastavnik> getNastavnicis() {
        return nastavnicis;
    }

    public OstaliPodaci nastavnicis(Set<Nastavnik> nastavniks) {
        this.nastavnicis = nastavniks;
        return this;
    }

    public OstaliPodaci addNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.add(nastavnik);
        nastavnik.getOstaliPodacis().add(this);
        return this;
    }

    public OstaliPodaci removeNastavnici(Nastavnik nastavnik) {
        this.nastavnicis.remove(nastavnik);
        nastavnik.getOstaliPodacis().remove(this);
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
        if (!(o instanceof OstaliPodaci)) {
            return false;
        }
        return id != null && id.equals(((OstaliPodaci) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OstaliPodaci{" +
            "id=" + getId() +
            ", ostalo='" + getOstalo() + "'" +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
