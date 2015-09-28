package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Exception causée par une annexe non trouve
 */
public class AnnexeIntrouvableException extends DomainException {

    private static final long serialVersionUID = 2276585885787963698L;

    //TODO : Ajouter un message associé à cette exception et vérifier dans les tests unitaires que ce message existe
}
