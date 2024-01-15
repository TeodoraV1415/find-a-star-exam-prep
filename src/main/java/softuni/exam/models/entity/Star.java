package softuni.exam.models.entity;

import softuni.exam.models.enums.StarType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stars")
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double lightYears;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StarType starType;

    @OneToMany(mappedBy = "observingStar", fetch = FetchType.EAGER)
    private Set<Astronomer> observers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "constellation_id")
    private Constellation constellation;

    public Long getId() {
        return id;
    }

    public Star setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Star setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public Star setLightYears(Double lightYears) {
        this.lightYears = lightYears;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Star setDescription(String description) {
        this.description = description;
        return this;
    }

    public StarType getStarType() {
        return starType;
    }

    public Star setStarType(StarType starType) {
        this.starType = starType;
        return this;
    }

    public Set<Astronomer> getObservers() {
        return observers;
    }

    public Star setObservers(Set<Astronomer> observers) {
        this.observers = observers;
        return this;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public Star setConstellation(Constellation constellation) {
        this.constellation = constellation;
        return this;
    }
}
