package ch.vd.demaut.domain.demandes.autorisation.repo;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.utilisateurs.Login;

import java.util.List;

/**
 * Repository des {@link DemandeAutorisation}
 */
@Repository
public interface DemandeAutorisationRepository
        extends GenericRepository<DemandeAutorisation, Long>, GenericReadRepository<DemandeAutorisation, Long> {

    /**
     * Récupère une demande par référence de demande <br>
     * Lève une exception si pas trouvée
     *
     * @param referenceDeDemande ReferenceDeDemande
     * @return La demande récupérée
     * @throws EntityNotFoundException
     */
    DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande);

    /**
     * Renvoie une demande dans l'état Brouillon associée à un Utilisateur. <br>
     * Renvoie exception si elle n'est pas trouvée ou s'il y en a plusieurs
     *
     * @param login Login
     * @return Une Demande autorisation dans l'état Brouillon et associée à
     * l'utilisateur
     */
    DemandeAutorisation recupererBrouillon(Login login);

    List<DemandeAutorisation> recupererListeBrouillons(Login login);

    /**
     * Teste si l'utilisateur a un brouillon associé
     *
     * @param login Login
     * @return boolean
     */
    boolean brouillonExiste(Login login);
}
