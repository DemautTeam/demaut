package ch.vd.demaut.microbiz.progreSoa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RefElement {

    private String id;

    private String libl;

    public RefElement() {
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getLibl() {
        return libl;
    }

    public void setLibl(String libl) {
        this.libl = libl;
    }
}
