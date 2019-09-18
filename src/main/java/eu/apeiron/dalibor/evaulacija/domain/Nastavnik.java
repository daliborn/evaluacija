package eu.apeiron.dalibor.evaulacija.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Nastavnik.
 */
@Entity
@Table(name = "nastavnik")
public class Nastavnik implements Serializable {

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

    @NotNull
    @Column(name = "titula", nullable = false)
    private String titula;

    @Lob
    @Column(name = "fotografija")
    private byte[] fotografija;

    @Column(name = "fotografija_content_type")
    private String fotografijaContentType;

    @ManyToMany
    @JoinTable(name = "nastavnik_zavrsni_radovi",
               joinColumns = @JoinColumn(name = "nastavnik_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "zavrsni_radovi_id", referencedColumnName = "id"))
    private Set<ZavrsniRad> zavrsniRadovis = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "nastavnik_projekti",
               joinColumns = @JoinColumn(name = "nastavnik_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "projekti_id", referencedColumnName = "id"))
    private Set<Projekat> projektis = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "nastavnik_mentorski_rad",
               joinColumns = @JoinColumn(name = "nastavnik_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "mentorski_rad_id", referencedColumnName = "id"))
    private Set<MentorskiRad> mentorskiRads = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "nastavnik_aktivnosti",
               joinColumns = @JoinColumn(name = "nastavnik_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "aktivnosti_id", referencedColumnName = "id"))
    private Set<Aktivnosti> aktivnostis = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "nastavnik_ostali_podaci",
               joinColumns = @JoinColumn(name = "nastavnik_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ostali_podaci_id", referencedColumnName = "id"))
    private Set<OstaliPodaci> ostaliPodacis = new HashSet<>();

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

    public Nastavnik ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Nastavnik prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTitula() {
        return titula;
    }

    public Nastavnik titula(String titula) {
        this.titula = titula;
        return this;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    public byte[] getFotografija() {
        return fotografija;
    }

    public Nastavnik fotografija(byte[] fotografija) {
        this.fotografija = fotografija;
        return this;
    }

    public void setFotografija(byte[] fotografija) {
        this.fotografija = fotografija;
    }

    public String getFotografijaContentType() {
        return fotografijaContentType;
    }

    public Nastavnik fotografijaContentType(String fotografijaContentType) {
        this.fotografijaContentType = fotografijaContentType;
        return this;
    }

    public void setFotografijaContentType(String fotografijaContentType) {
        this.fotografijaContentType = fotografijaContentType;
    }

    public Set<ZavrsniRad> getZavrsniRadovis() {
        return zavrsniRadovis;
    }

    public Nastavnik zavrsniRadovis(Set<ZavrsniRad> zavrsniRads) {
        this.zavrsniRadovis = zavrsniRads;
        return this;
    }

    public Nastavnik addZavrsniRadovi(ZavrsniRad zavrsniRad) {
        this.zavrsniRadovis.add(zavrsniRad);
        zavrsniRad.getNastavnicis().add(this);
        return this;
    }

    public Nastavnik removeZavrsniRadovi(ZavrsniRad zavrsniRad) {
        this.zavrsniRadovis.remove(zavrsniRad);
        zavrsniRad.getNastavnicis().remove(this);
        return this;
    }

    public void setZavrsniRadovis(Set<ZavrsniRad> zavrsniRads) {
        this.zavrsniRadovis = zavrsniRads;
    }

    public Set<Projekat> getProjektis() {
        return projektis;
    }

    public Nastavnik projektis(Set<Projekat> projekats) {
        this.projektis = projekats;
        return this;
    }

    public Nastavnik addProjekti(Projekat projekat) {
        this.projektis.add(projekat);
        projekat.getNastavnicis().add(this);
        return this;
    }

    public Nastavnik removeProjekti(Projekat projekat) {
        this.projektis.remove(projekat);
        projekat.getNastavnicis().remove(this);
        return this;
    }

    public void setProjektis(Set<Projekat> projekats) {
        this.projektis = projekats;
    }

    public Set<MentorskiRad> getMentorskiRads() {
        return mentorskiRads;
    }

    public Nastavnik mentorskiRads(Set<MentorskiRad> mentorskiRads) {
        this.mentorskiRads = mentorskiRads;
        return this;
    }

    public Nastavnik addMentorskiRad(MentorskiRad mentorskiRad) {
        this.mentorskiRads.add(mentorskiRad);
        mentorskiRad.getNastavnicis().add(this);
        return this;
    }

    public Nastavnik removeMentorskiRad(MentorskiRad mentorskiRad) {
        this.mentorskiRads.remove(mentorskiRad);
        mentorskiRad.getNastavnicis().remove(this);
        return this;
    }

    public void setMentorskiRads(Set<MentorskiRad> mentorskiRads) {
        this.mentorskiRads = mentorskiRads;
    }

    public Set<Aktivnosti> getAktivnostis() {
        return aktivnostis;
    }

    public Nastavnik aktivnostis(Set<Aktivnosti> aktivnostis) {
        this.aktivnostis = aktivnostis;
        return this;
    }

    public Nastavnik addAktivnosti(Aktivnosti aktivnosti) {
        this.aktivnostis.add(aktivnosti);
        aktivnosti.getNastavnicis().add(this);
        return this;
    }

    public Nastavnik removeAktivnosti(Aktivnosti aktivnosti) {
        this.aktivnostis.remove(aktivnosti);
        aktivnosti.getNastavnicis().remove(this);
        return this;
    }

    public void setAktivnostis(Set<Aktivnosti> aktivnostis) {
        this.aktivnostis = aktivnostis;
    }

    public Set<OstaliPodaci> getOstaliPodacis() {
        return ostaliPodacis;
    }

    public Nastavnik ostaliPodacis(Set<OstaliPodaci> ostaliPodacis) {
        this.ostaliPodacis = ostaliPodacis;
        return this;
    }

    public Nastavnik addOstaliPodaci(OstaliPodaci ostaliPodaci) {
        this.ostaliPodacis.add(ostaliPodaci);
        ostaliPodaci.getNastavnicis().add(this);
        return this;
    }

    public Nastavnik removeOstaliPodaci(OstaliPodaci ostaliPodaci) {
        this.ostaliPodacis.remove(ostaliPodaci);
        ostaliPodaci.getNastavnicis().remove(this);
        return this;
    }

    public void setOstaliPodacis(Set<OstaliPodaci> ostaliPodacis) {
        this.ostaliPodacis = ostaliPodacis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nastavnik)) {
            return false;
        }
        return id != null && id.equals(((Nastavnik) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nastavnik{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", titula='" + getTitula() + "'" +
            ", fotografija='" + getFotografija() + "'" +
            ", fotografijaContentType='" + getFotografijaContentType() + "'" +
            "}";
    }
}
