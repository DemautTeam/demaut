package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class SiteInternet extends BaseValueObject {

    private String value;

    // ********************************************************* Constructor

    //Used only for JPA
    SiteInternet() {
    }

    public SiteInternet(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
