package ch.vd.demaut.services.annexes.mock;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.services.annexes.AnnexesService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.mockito.Mockito.mock;

public class AnnexesServiceMock implements AnnexesService {

    private static AnnexesServiceMock INSTANCE = null;

    public synchronized static AnnexesServiceMock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(AnnexesServiceMock.class);
        }
        return INSTANCE;
    }

    @Override
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande) {
        // TODO
        return null;
    }

    @Override
    public ContenuAnnexe afficherUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier) {
        // TODO
        return null;
    }

    @Override
    public boolean attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier, TypeAnnexe typeAnnexe) throws IOException {
        // TODO
        return false;
    }

    @Override
    public boolean supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier, TypeAnnexe typeAnnexe) {
        // TODO
        return false;
    }
}
