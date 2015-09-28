package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;

import java.util.List;

public interface ProgreSoaService {

    List<ReportedOrganisationType> rechercheSOATierById(String baseUri, String organisationId) throws Exception;

    List<ReportedOrganisationType> rechercheSOATierByNom(String baseUri, String organisationNom) throws Exception;

    RefRoot listeSOAAPTitre(String baseUri) throws Exception;

    RefRoot listeSOADiplomesFormationApprofondie(String baseUri) throws Exception;

    RefRoot listeSOADiplomesFormationComplementaire(String baseUri) throws Exception;

    RefRoot listeSOADiplomesFormationInitiale(String baseUri) throws Exception;

    RefRoot listeSOADiplomesFormationPostGrade(String baseUri) throws Exception;

    RefRoot listeSOAProfession(String baseUri) throws Exception;

    RefRoot listeSOATypesAnnexes(String baseUri) throws Exception;
}
