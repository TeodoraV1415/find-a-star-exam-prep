package softuni.exam.models.dto;

import softuni.exam.models.enums.StarType;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StarSeedDTO {

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 6)
    private String description;

    @Positive
    private Double lightYears;

    private StarType starType;

    private Long constellation;

    public StarSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public StarSeedDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StarSeedDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public StarSeedDTO setLightYears(Double lightYears) {
        this.lightYears = lightYears;
        return this;
    }

    public StarType getStarType() {
        return starType;
    }

    public StarSeedDTO setStarType(StarType starType) {
        this.starType = starType;
        return this;
    }

    public Long getConstellation() {
        return constellation;
    }

    public StarSeedDTO setConstellation(Long constellation) {
        this.constellation = constellation;
        return this;
    }
}
