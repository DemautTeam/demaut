package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import org.springframework.stereotype.Service;

@Service
public interface DemandeAutorisationService {

    DemandeAutorisation initialiserDemandeAutorisation();

    DemandeAutorisation suavegarderDemandeAutorisation(DemandeAutorisation demandeAutorisation);
}

