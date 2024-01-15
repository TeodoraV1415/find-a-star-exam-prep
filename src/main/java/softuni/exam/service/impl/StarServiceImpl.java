package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.enums.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: Implement all methods
@Service
public class StarServiceImpl implements StarService {

    private final String STARS_FILE_PATH = "src/main/resources/files/json/stars.json";
    private StarRepository starRepository;
    private ConstellationRepository constellationRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, Gson gson) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        String json = this.readStarsFileContent();

        StarSeedDTO[] starSeedDTOs = this.gson.fromJson(json, StarSeedDTO[].class);

        List<String> result = new ArrayList<>();

        for (StarSeedDTO starSeedDTO: starSeedDTOs) {
            Set<ConstraintViolation<StarSeedDTO>> errors = this.validator.validate(starSeedDTO);

           if (errors.isEmpty()){
               Optional<Star> optStar = this.starRepository.findByName(starSeedDTO.getName());

               if (optStar.isEmpty()){
                   Star mapped = this.modelMapper.map(starSeedDTO, Star.class);
                   Constellation constellation = constellationRepository.findById(starSeedDTO.getConstellation()).orElse(null);
                   constellation.getStars().add(mapped);
                   constellationRepository.save(constellation);
                   mapped.setConstellation(constellation);
                   this.starRepository.save(mapped);
                   result.add(String.format("Successfully imported star %s - %.2f light years", starSeedDTO.getName(), starSeedDTO.getLightYears()));
               } else {
                   result.add("Invalid star");
               }
           } else {
               result.add("Invalid star");
           }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportStars() {
        Set<Star> allByTypeAndObserverIsNull = this.starRepository.findAllByStarTypeAndObserversIsNullOrderByLightYearsAsc(StarType.RED_GIANT);

        return allByTypeAndObserverIsNull.stream()
                .map(star -> String.format("Star: %s\n" +
                                "   *Distance: %.2f light years\n" +
                                "   **Description: %s\n" +
                                "   ***Constellation: %s",
                        star.getName(),
                        star.getLightYears(),
                        star.getDescription(),
                        star.getConstellation().getName()))
                .collect(Collectors.joining("\n")).trim();
    }
}
