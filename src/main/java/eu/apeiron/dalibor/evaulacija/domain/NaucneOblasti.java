package eu.apeiron.dalibor.evaulacija.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A NaucneOblasti.
 */
@Entity
@Table(name = "naucne_oblasti")
public class NaucneOblasti implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oblast")
    private String oblast;

    @ManyToOne
    @JsonIgnoreProperties("oblastis")
    private ZavrsniRad zavrsniRadovi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOblast() {
        return oblast;
    }

    public NaucneOblasti oblast(String oblast) {
        this.oblast = oblast;
        return this;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public ZavrsniRad getZavrsniRadovi() {
        return zavrsniRadovi;
    }

    public NaucneOblasti zavrsniRadovi(ZavrsniRad zavrsniRad) {
        this.zavrsniRadovi = zavrsniRad;
        return this;
    }

    public void setZavrsniRadovi(ZavrsniRad zavrsniRad) {
        this.zavrsniRadovi = zavrsniRad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NaucneOblasti)) {
            return false;
        }
        return id != null && id.equals(((NaucneOblasti) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NaucneOblasti{" +
            "id=" + getId() +
            ", oblast='" + getOblast() + "'" +
            "}";
    }
}
