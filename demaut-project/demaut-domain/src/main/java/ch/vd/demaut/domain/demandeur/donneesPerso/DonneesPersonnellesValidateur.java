package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;
import ch.vd.demaut.domain.exception.DonneesPersonnellesNonValideException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class DonneesPersonnellesValidateur extends AbstractDataValidateur<DonneesPersonnelles> {

    // ********************************************************* Static fields

    // ********************************************************* Fields

    // ********************************************************* Constructeur

    // ********************************************************* Méthodes métier

    /**
     * Vérifie que l'annexe est valide. Si non valide, renvoie une
     * {@link AnnexeNonValideException}
     */
    @Override
    public void valider(DonneesPersonnelles donneesPersonnelles) {
        Set<ConstraintViolation<DonneesPersonnelles>> constraintViolationsResult = validateData(donneesPersonnelles);

        if (constraintViolationsResult.size() > 0) {
            throw new DonneesPersonnellesNonValideException();
        }
        validateNationalitePermis(donneesPersonnelles);

    }

    private void validateNationalitePermis(DonneesPersonnelles donneesPersonnelles){
        if (donneesPersonnelles.getNationalite() != Pays.Suisse){
            if(donneesPersonnelles.getPermis() == null || donneesPersonnelles.getPermis().getTypePermis() == TypePermis.Aucun){
                throw new DonneesPersonnellesNonValideException("Le permis n'est pas renseigné");
            } else if(donneesPersonnelles.getPermis().getTypePermis() == TypePermis.Autre && StringUtils.isEmpty(donneesPersonnelles.getPermis().getAutrePermis().getValue())){
                throw new DonneesPersonnellesNonValideException("Pas de précisions pour un permis de type Autre");
            }
        }
    }

}
