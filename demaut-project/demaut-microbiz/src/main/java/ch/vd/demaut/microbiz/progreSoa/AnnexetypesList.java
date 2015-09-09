package ch.vd.demaut.microbiz.progreSoa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class AnnexetypesList {

    private List<String> lesTypesAnnexes = new ArrayList<>();

    public AnnexetypesList() {
    }

    public void populate() {
        this.lesTypesAnnexes = new ArrayList<String>() {
            {
                add("Liste mockée");
                add("Curriculum vitae");
                add("Diplôme (médecin)");
                add("Titre (pédiatre)");
                add("Certificats de Travail");
                add("Casier judiciaire");
            }
        };
    }

    @XmlElement
    public List<String> getLesTypesAnnexes() {
        return lesTypesAnnexes;
    }

    public void setLesTypesAnnexes(List<String> lesTypesAnnexes) {
        this.lesTypesAnnexes = lesTypesAnnexes;
    }
}
