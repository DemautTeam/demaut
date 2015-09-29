package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ch.vd.demaut.commons.exceptions.NotYetImplementedException;
import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;

@Repository
public class DemandeAutorisationRepositoryJPA extends GenericRepositoryImpl<DemandeAutorisation, Long>
        implements DemandeAutorisationRepository {

    // ********************************************************* Constructor
    public DemandeAutorisationRepositoryJPA() {
        super(DemandeAutorisation.class);
    }

    // ********************************************************* Implementation des interfaces
    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref) {
        TypedQuery<DemandeAutorisation> typedQuery = createQueryParReference(ref);
        return typedQuery.getSingleResult();
    }

    @Override
    public DemandeAutorisation trouverDemandeEnCoursDeSaisieDunUtilisateur(Utilisateur utilisateur) {
        //TODO: Implement me
        throw new NotYetImplementedException();
    }

    // ********************************************************* Methodes privées
    private TypedQuery<DemandeAutorisation> createQueryParReference(ReferenceDeDemande ref) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(builder.equal(autorisationRoot.get("referenceDeDemande").get("value"), ref.getValue()));
        return this.getEntityManager().createQuery(criteriaQuery);
    }

}
