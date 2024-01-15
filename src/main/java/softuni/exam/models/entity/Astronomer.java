package softuni.exam.models.entity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "astronomers")
public class Astronomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "average_observation_hours", nullable = false)
    private Double averageObservationHours;

    @Column(nullable = true)
    private LocalDate birthday;

    @ManyToOne
    private Star observingStar;

    public Long getId() {
        return id;
    }

    public Astronomer setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Astronomer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Astronomer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public Astronomer setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public Astronomer setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Astronomer setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public Star getObservingStar() {
        return observingStar;
    }

    public Astronomer setObservingStar(Star observingStar) {
        this.observingStar = observingStar;
        return this;
    }
}
