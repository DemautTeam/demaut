package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DemandeAutorisationService {

    DemandeAutorisation initialiserDemandeAutorisation();

    DemandeAutorisation sauvegarderDemandeAutorisation(DemandeAutorisation demandeAutorisation);

    List<Annexe> listerLesAnnexes();

    boolean attacherUneAnnexe(Annexe annexe);
}

