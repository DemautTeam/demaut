package ch.vd.demaut.services.demandes.autorisation.impl;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.factory.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAutorisationServiceImpl.class);

	@Autowired
	private DemandeAutorisationRepository demandeAutorisationRepository;

	private DemandeAutorisationFactory demandeAutorisationFactory = DemandeAutorisationFactory.getInstance();

	@Override
	public DemandeAutorisation initialiserDemandeAutorisation() {
		return demandeAutorisationFactory.inititierDemandeAutorisation();
	}

	@Override
	public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
		return demandeAutorisationRepository.afficherUneDemandeAutorisation(demandeReference);
	}

	public void sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
		demandeAutorisationRepository.store(demandeAutorisation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Annexe> listerLesAnnexes(String demandeReference) {
		DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
		return demandeAutorisation != null ? demandeAutorisation.listerLesAnnexes() : new ArrayList<Annexe>();
	}

	@Override
	public Annexe afficherUneAnnexe(String demandeReference, String annexeFileName) {
		DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
		return demandeAutorisation != null ? demandeAutorisation.afficherUneAnnexe(annexeFileName) : null;
	}

	@Override
	public boolean attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize,
			String annexeFileType, String annexeType) {
		DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
		if (demandeAutorisation != null) {
			try {
				Annexe annexe = new Annexe(TypeAnnexe.valueOf(annexeType), annexeFileName,
						IOUtils.toByteArray(new FileInputStream(file)));
				demandeAutorisation.attacherUneAnnexe(annexe);
				sauverLaDemandeAutorisation(demandeAutorisation);
			} catch (IOException e) {
				LOGGER.error("Exception attacherUneAnnexe " + e.getMessage());
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType) {
		DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
		if (demandeAutorisation != null) {
			demandeAutorisation.supprimerUneAnnexe(annexeFileName);
			demandeAutorisationRepository.store(demandeAutorisation);
			return true;
		}
		return false;
	}
}
