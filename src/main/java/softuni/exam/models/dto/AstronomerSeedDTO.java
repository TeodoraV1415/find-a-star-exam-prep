package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedDTO {

    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;

    @XmlElement(name = "salary")
    @DecimalMin(value = "15000")
    @NotNull
    private Double salary;

    @XmlElement(name = "average_observation_hours")
    @NotNull
    @DecimalMin(value = "500")
    private Double averageObservationHours;

    @XmlElement(name = "birthday")
    private String birthday;

    @XmlElement(name = "observing_star_id")
    private Long observingStar;

    public AstronomerSeedDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public AstronomerSeedDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AstronomerSeedDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public AstronomerSeedDTO setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public AstronomerSeedDTO setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public AstronomerSeedDTO setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public Long getObservingStar() {
        return observingStar;
    }

    public AstronomerSeedDTO setObservingStar(Long observingStar) {
        this.observingStar = observingStar;
        return this;
    }
}
