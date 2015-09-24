package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.LocalDateVO;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class DateDeNaissance extends LocalDateVO {

    // ********************************************************* Fields
    public DateDeNaissance(LocalDate value) {
        super(value);
    }

    // ********************************************************* Business Methods


    @NotNull
    @Past
    @Override
    public LocalDate getLocalDate() {
        return super.getLocalDate();
    }
}
