package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

//TODO: A virer absolument. Pas besoin de Reference De Diplome ! 
@ValueObject
public class ReferenceDeDiplome extends StringVO {

    // ********************************************************* Fields

    // ********************************************************* Constructor

    public ReferenceDeDiplome() {
    }

    public ReferenceDeDiplome(String reference) {
        super(reference);
    }

    // ********************************************************* Getters

}
