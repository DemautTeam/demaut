package ch.vd.demaut.services.demandes.autorisation.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.AnnexeNonValideException;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
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
	
	@Override
	public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref) {
		return demandeAutorisationRepository.recupererDemandeParReference(ref);
	}

	/**
	 * L'annexe retournée de préférence ne devrait PAS contenir le contunu
	 * stream pour la consultation front
	 *
	 * @param demandeReference
	 *            String
	 * @return Collection AnnexeMetadata
	 */
	@Override
	public Collection<AnnexeMetadata> listerLesAnnexesMetadatas(ReferenceDeDemande ref) {
		DemandeAutorisation demandeAutorisation = recupererDemandeParReference(ref);
		Collection<AnnexeMetadata> annexesMetadatas = demandeAutorisation.listerLesAnnexeMetadatas();
		return annexesMetadatas;
	}

	/**
	 * L'annexe retournée DOIT absolument contenir le contunu stream pour la
	 * consultation front
	 *
	 * @param demandeReference
	 *            String
	 * @param annexeFileName
	 *            String
	 * @return Annexe
	 */
	@Override
	public ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande ref, NomFichier nomFichier) {
		DemandeAutorisation demandeAutorisation = recupererDemandeParReference(ref);

		ContenuAnnexe contenu = demandeAutorisation.extraireContenuAnnexe(nomFichier);

		return contenu;
	}

	@Transactional
	@Override
	public void attacherUneAnnexe(ReferenceDeDemande ref, File file, NomFichier nomFichier, TypeAnnexe type) {
		DemandeAutorisation demandeAutorisation = recupererDemandeParReference(ref);
		
		ContenuAnnexe contenuAnnexe = buildContenuAnnexe(file);
		
		Annexe annexe = new Annexe(type, nomFichier, contenuAnnexe);
		
		demandeAutorisation.validerEtAttacherAnnexe(annexe);
		
	}

	@Override
	public void supprimerUneAnnexe(ReferenceDeDemande ref, NomFichier nomFichier) {
		DemandeAutorisation demandeAutorisation = recupererDemandeParReference(ref);
		
		demandeAutorisation.supprimerUneAnnexeParNomFichier(nomFichier);
	}

	/*
    @Override
    public ProfessionDeLaSante afficherDonneesProfession(String demandeReference) {
        DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
        return demandeAutorisation != null ? demandeAutorisation.getProfessionDeLaSante() : null;
    }

    @Override
    public String renseignerDonneesProfession(String demandeReference, String idProfession, String codeGln) {
        if (!StringUtils.isEmpty(demandeReference) && this.afficherUneDemandeAutorisation(demandeReference) != null) {
                // TODO setter la profession/codeGln si demandeAutorisation le permet
                return demandeReference;
        } else {
            DemandeAutorisation demandeAutorisation = this.initialiserDemandeAutorisation(
                    new Demandeur(
                            new NomEtPrenomDemandeur(null, null),
                            new DonneesProfessionnelles(new CodeGLN(codeGln))),
                    ProfessionDeLaSante.valueOf(idProfession), null);
            return demandeAutorisation.getReferenceDeDemande().getReference();
        }
    }
    */

    private ContenuAnnexe buildContenuAnnexe(File file) {
		byte[] contenu;
		try {
			contenu = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			//TODO: faire une exeception plus explicite
			throw new AnnexeNonValideException();
		} catch (IOException e) {
			throw new AnnexeNonValideException();
		}
		ContenuAnnexe contenuAnnexe = new ContenuAnnexe(contenu);
		return contenuAnnexe;
	}

}
