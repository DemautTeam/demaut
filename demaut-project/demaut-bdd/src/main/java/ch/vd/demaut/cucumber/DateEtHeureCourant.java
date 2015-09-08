package ch.vd.demaut.cucumber;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.DateTimeVO;
import org.joda.time.DateTime;

@ValueObject
public class DateEtHeureCourant extends DateTimeVO {

    // ********************************************************* Constructor
    public DateEtHeureCourant(DateTime dateHeureCourant) {
        super(dateHeureCourant);
    }

    // ********************************************************* Business Methods


}
