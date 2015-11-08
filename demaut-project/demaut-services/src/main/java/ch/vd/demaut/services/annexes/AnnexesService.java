package ch.vd.demaut.services.annexes;

import java.io.File;
import java.util.Collection;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

public interface AnnexesService {

    /**
     * Renvoie la liste des annexes attachées à une demande
     * @param referenceDeDemande reference unique d'une demande
     * @return
     */
    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande);

    Collection<TypeAnnexe> listerLesTypeAnnexesObligatoires(ReferenceDeDemande referenceDeDemande);

    ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK);

    //TODO : Supprimer cette methode redondante
    void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier);

    void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, Annexe annexeALier);

    void supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK);

}
