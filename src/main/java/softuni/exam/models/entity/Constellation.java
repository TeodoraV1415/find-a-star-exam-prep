package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "constellations")
public class Constellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "constellation", fetch = FetchType.EAGER)
    private Set<Star> stars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Constellation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Constellation setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Constellation setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Star> getStars() {
        return stars;
    }

    public Constellation setStars(Set<Star> stars) {
        this.stars = stars;
        return this;
    }
}
