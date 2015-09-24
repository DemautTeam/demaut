package ch.vd.demaut.services.annexes.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.AnnexeNonValideException;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@Service("annexesService")
public class AnnexesServiceImpl implements AnnexesService {

	// ********************************************************* Services injectes
	@Inject
	private DemandeAutorisationService demandeAutorisationService;

	// ********************************************************* Implémentation Services
	
	
	/**
	 * Renvoie la liste des {@link AnnexeMetadata} pour une demande identifiée
	 * par une {@link ReferenceDeDemande}
	 *
	 * @param demandeReference
	 *            String
	 * @return Collection AnnexeMetadata
	 */
	@Transactional(readOnly = true)
	@Override
	public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande ref) {
		DemandeAutorisation demandeAutorisation = recupererDemnandeParRef(ref);
		Collection<AnnexeMetadata> annexesMetadatas = demandeAutorisation.listerLesAnnexeMetadatas();
		return annexesMetadatas;
	}

	/**
	 * Renvoie le {@link ContenuAnnexe} d'une {@link Annexe} en fonction de la
	 * {@link DemandeAutorisation} et de son {@link NomFichier}
	 *
	 * @param demandeReference
	 *            String
	 * @param annexeFileName
	 *            String
	 * @return Annexe
	 */
	@Transactional(readOnly = true)
	@Override
	public ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande ref, NomFichier nomFichier) {
		DemandeAutorisation demandeAutorisation = recupererDemnandeParRef(ref);

		ContenuAnnexe contenu = demandeAutorisation.extraireContenuAnnexe(nomFichier);

		return contenu;
	}

	/**
	 * Attache une {@link Annexe} à une {@link DemandeAutorisation}
	 */
	@Transactional
	@Override
	public void attacherUneAnnexe(ReferenceDeDemande ref, File file, NomFichier nomFichier, TypeAnnexe type) {

		ContenuAnnexe contenuAnnexe = buildContenuAnnexe(file);

		Annexe annexe = new Annexe(type, nomFichier, contenuAnnexe);

		attacherAnnexe(ref, annexe);

	}

	/**
	 * Attache une {@link Annexe} à une {@link DemandeAutorisation}
	 */
	@Transactional
	@Override
	public void attacherUneAnnexe(ReferenceDeDemande ref, Annexe annexe) {
		attacherAnnexe(ref, annexe);
	}
	

	@Transactional
	@Override
	public void supprimerUneAnnexe(ReferenceDeDemande ref, NomFichier nomFichier) {
		DemandeAutorisation demandeAutorisation = recupererDemnandeParRef(ref);

		demandeAutorisation.supprimerUneAnnexeParNomFichier(nomFichier);
	}

	private void attacherAnnexe(ReferenceDeDemande ref, Annexe annexe) {
		DemandeAutorisation demandeAutorisation = recupererDemnandeParRef(ref);

		demandeAutorisation.validerEtAttacherAnnexe(annexe);
	}

	// ********************************************************* Methodes privees

	private ContenuAnnexe buildContenuAnnexe(File file) {
		byte[] contenu;
		try {
			contenu = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO: Faire une exception plus explicite
			throw new AnnexeNonValideException();
		} catch (IOException e) {
			throw new AnnexeNonValideException();
		}
		ContenuAnnexe contenuAnnexe = new ContenuAnnexe(contenu);
		return contenuAnnexe;
	}

	private DemandeAutorisation recupererDemnandeParRef(ReferenceDeDemande ref) {
		return demandeAutorisationService.recupererDemandeParReference(ref);
	}

}
