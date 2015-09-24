package ch.vd.demaut.services.demandes.autorisation.impl;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

	@Inject
	private DemandeAutorisationRepository demandeAutorisationRepository;
	
	@Inject
	private DemandeAutorisationFactory demandeAutorisationFactory;

	@Transactional
	@Override
	public DemandeAutorisation initialiserDemandeAutorisation(Login login, ProfessionDeLaSante profession) {
		DemandeAutorisation nouvelleDemande = demandeAutorisationFactory.inititierDemandeAutorisation(login, profession, null);
		
		demandeAutorisationRepository.store(nouvelleDemande);
				
		return nouvelleDemande;
	}
	
	@Transactional(readOnly=true)
	@Override
	public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref) {
		return demandeAutorisationRepository.recupererDemandeParReference(ref);
	}

}
