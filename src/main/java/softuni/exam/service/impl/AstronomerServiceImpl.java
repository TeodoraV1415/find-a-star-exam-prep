package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerSeedDTO;
import softuni.exam.models.dto.AstronomerSeedRootDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.XmlParser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// TODO: Implement all methods
@Service
public class AstronomerServiceImpl implements AstronomerService {

    private final String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";
    private AstronomerRepository astronomerRepository;
    private ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final Validator validator;
    private StarRepository starRepository;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, XmlParser xmlParser, StarRepository starRepository) throws JAXBException {
        this.astronomerRepository = astronomerRepository;
        this.xmlParser = xmlParser;
        this.starRepository = starRepository;
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        AstronomerSeedRootDTO astronomerSeedRootDTO = this.xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomerSeedRootDTO.class);
        List<AstronomerSeedDTO> astronomersDTOs = astronomerSeedRootDTO.getAstronomersDTOs();

        List<String> result = new ArrayList<>();

        for (AstronomerSeedDTO astronomerSeedDTO : astronomersDTOs) {
            Set<ConstraintViolation<AstronomerSeedDTO>> errors = this.validator.validate(astronomerSeedDTO);

            if (errors.isEmpty()){

                Optional<Astronomer> optAstronomer = this.astronomerRepository.findByFirstNameAndLastName(astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName());
                if (optAstronomer.isEmpty()){
                   Optional<Star> optStar = starRepository.findById(astronomerSeedDTO.getObservingStar());
                    if (optStar.isEmpty()){
                        result.add("Invalid astronomer");
                    } else {
                        Astronomer astronomer = modelMapper.map(astronomerSeedDTO, Astronomer.class);

                        Star star = optStar.get();
                        star.getObservers().add(astronomer);
                        astronomer.setObservingStar(star);
                        LocalDate birthday = LocalDate.parse(astronomerSeedDTO.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        astronomer.setBirthday(birthday);
                        starRepository.save(star);
                        astronomerRepository.save(astronomer);
                        result.add(String.format("Successfully imported astronomer %s %s - %.2f",astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName(), astronomerSeedDTO.getAverageObservationHours()));
                    }

                } else {
                    result.add("Invalid astronomer");
                }
            } else {
                result.add("Invalid astronomer");
            }

        }
        
        return String.join("\n", result);
    }
}
