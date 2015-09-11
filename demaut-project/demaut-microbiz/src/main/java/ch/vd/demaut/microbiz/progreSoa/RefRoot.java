package ch.vd.demaut.microbiz.progreSoa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlSeeAlso({RefElement.class})
public class RefRoot extends ArrayList<RefElement> {

    private String type;

    private List<RefElement> refElements = new ArrayList<>();

    public RefRoot() {
    }

    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "refElement")
    public List<RefElement> getRefElements() {
        return refElements;
    }

    public void setRefElements(List<RefElement> refElements) {
        this.refElements = refElements;
    }
}
