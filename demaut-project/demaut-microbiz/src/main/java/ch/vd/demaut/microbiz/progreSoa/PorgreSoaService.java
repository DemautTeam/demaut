package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;

import java.util.List;

public interface PorgreSoaService {

    List<ReportedOrganisationType> rechercheSOATierById(String organisationId) throws Exception;

    List<ReportedOrganisationType> rechercheSOATierByNom(String organisationNom) throws Exception;

    RefRoot listeSOAFormationApprofondie() throws Exception;

    RefRoot listeSOAFormationComplementaire() throws Exception;

    RefRoot listeSOAFormationInitiale() throws Exception;

    RefRoot listeSOADiplomesPostGrade() throws Exception;

    RefRoot listeSOAProfession() throws Exception;

    RefRoot listeSOATypesAnnexes() throws Exception;
}
