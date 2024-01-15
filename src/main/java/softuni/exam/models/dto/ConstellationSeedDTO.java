package softuni.exam.models.dto;

import javax.validation.constraints.Size;

public class ConstellationSeedDTO {

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 5)
    private String description;

    public ConstellationSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public ConstellationSeedDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ConstellationSeedDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
