package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Exception causée par un diplome non trouve
 */
public class DiplomeIntrouvableException extends DomainException {

    private static final long serialVersionUID = 2276585885787963698L;

    //TODO : Ajouter un message associé à cette exception et vérifier dans les tests unitaires que ce message existe
}
