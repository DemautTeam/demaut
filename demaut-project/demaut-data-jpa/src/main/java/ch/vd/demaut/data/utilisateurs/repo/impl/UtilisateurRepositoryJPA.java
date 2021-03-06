package ch.vd.demaut.data.utilisateurs.repo.impl;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

public class UtilisateurRepositoryJPA extends GenericRepositoryImpl<Utilisateur, Long>
        implements UtilisateurRepository {

    public UtilisateurRepositoryJPA() {
        super(Utilisateur.class);
    }
}
