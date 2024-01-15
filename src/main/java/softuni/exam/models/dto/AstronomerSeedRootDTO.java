package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedRootDTO {

    @XmlElement(name = "astronomer")
    private List<AstronomerSeedDTO> astronomersDTOs;

    public AstronomerSeedRootDTO() {
    }

    public List<AstronomerSeedDTO> getAstronomersDTOs() {
        return astronomersDTOs;
    }

    public AstronomerSeedRootDTO setAstronomersDTOs(List<AstronomerSeedDTO> astronomersDTOs) {
        this.astronomersDTOs = astronomersDTOs;
        return this;
    }
}
