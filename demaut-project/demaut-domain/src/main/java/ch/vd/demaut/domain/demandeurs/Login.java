package ch.vd.demaut.domain.demandeurs;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class Login extends BaseValueObject {
    // ********************************************************* Fields
    private String value;

    // ********************************************************* Constructor
    protected Login(){
    }
    
    public Login(String value) {
        super();
        this.value = value;
    }

    // ********************************************************* Getters
    @NotNull
    public String getValue() {
        return value;
    }

}
