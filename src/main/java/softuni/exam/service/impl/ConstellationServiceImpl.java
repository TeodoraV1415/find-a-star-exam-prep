package softuni.exam.service.impl;

// TODO: Implement all methods

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private final String CONSTELLATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";
    private ConstellationRepository constellationRepository;
    private final Validator validator;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0 ;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        String json = this.readConstellationsFromFile();

        ConstellationSeedDTO[] constellationSeedDTOs = this.gson.fromJson(json, ConstellationSeedDTO[].class);

        List<String> result = new ArrayList<>();

        for (ConstellationSeedDTO constellationSeedDTO : constellationSeedDTOs){

            Set<ConstraintViolation<ConstellationSeedDTO>> errors = this.validator.validate(constellationSeedDTO);
            if (errors.isEmpty()){
                Optional<Constellation> optionalConstellation = this.constellationRepository.findByName(constellationSeedDTO.getName());
                if (optionalConstellation.isEmpty()){

                    Constellation mapped = this.modelMapper.map(constellationSeedDTO, Constellation.class);
                    this.constellationRepository.save(mapped);
                    result.add(String.format("Successfully imported constellation %s - %s", constellationSeedDTO.getName(), constellationSeedDTO.getDescription()));
                } else {
                    result.add("Invalid constellation");
                }
            } else {
                result.add("Invalid constellation");
            }

        }

        return String.join("\n", result);
    }


}
