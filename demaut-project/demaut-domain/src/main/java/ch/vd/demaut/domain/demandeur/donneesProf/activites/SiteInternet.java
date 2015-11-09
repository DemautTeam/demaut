package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

@ValueObject
public class SiteInternet extends BaseValueObject implements StringVOInterface{

    private String value;

    // ********************************************************* Constructor

    //Used only for JPA
    protected SiteInternet() {
    }

    public SiteInternet(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
