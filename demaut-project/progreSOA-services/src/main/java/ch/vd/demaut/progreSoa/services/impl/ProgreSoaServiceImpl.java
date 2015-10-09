package ch.vd.demaut.progreSoa.services.impl;

import ch.vd.demaut.progreSoa.services.ProgreSoaService;
import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service("progreSoaService")
public class ProgreSoaServiceImpl implements ProgreSoaService {

    public static final String DEMAUT_PROF_PATH = "prof";
    public static final String DEMAUT_TIERS_PATH = "tiers";
    public static final String DEMAUT_SEARCH_PERS_PATH = "rechercherPersonneRI";
    public static final String DEMAUT_CONSULT_PERS_PATH = "consulterPersonneRI";
    public static final String DEMAUT_REF_PATH = "demautRef";
    public static final String DEMAUT_SEARCH_INDIV_PATH = "demautRecIndi";

    private Client client = ClientBuilder.newClient();

    @Value("${demaut.progre.soa.endpoint}")
    private String progreSOAUrl;

    @Override
    public List<ReportedOrganisationType> rechercheSOATierById(String baseUri, String organisationId) throws Exception {
        String forgeProgreSOAUrl = this.forgeProgreSOAUrl(baseUri);
        WebTarget target = client.target(forgeProgreSOAUrl);
        target = target.path(DEMAUT_TIERS_PATH).queryParam("id", organisationId);
        return target.request().get(ch.vd.ses.referentiel.tiers_v01.Root.class).getMoralPerson();
    }

    @Override
    public List<ReportedOrganisationType> rechercheSOATierByNom(String baseUri, String organisationNom)
            throws Exception {
        String forgeProgreSOAUrl = this.forgeProgreSOAUrl(baseUri);
        WebTarget target = client.target(forgeProgreSOAUrl);
        target = target.path(DEMAUT_TIERS_PATH).queryParam("nom", organisationNom);
        return target.request().get(ch.vd.ses.referentiel.tiers_v01.Root.class).getMoralPerson();
    }

    @Override
    public RefRoot listeSOAAPTitre(String baseUri) throws Exception {
        return processQuery(baseUri, "AP_TITRE");
    }

    public RefRoot listeSOADiplomesFormationApprofondie(String baseUri) throws Exception {
        return processQuery(baseUri, "D_FORMATION_APPROFONDIE");
    }

    @Override
    public RefRoot listeSOADiplomesFormationComplementaire(String baseUri) throws Exception {
        return processQuery(baseUri, "D_FORMATION_COMPLEMENTAIRE");
    }

    @Override
    public RefRoot listeSOADiplomesFormationInitiale(String baseUri) throws Exception {
        return processQuery(baseUri, "D_FORMATION_INITIALE");
    }

    @Override
    public RefRoot listeSOADiplomesFormationPostGrade(String baseUri) throws Exception {
        return processQuery(baseUri, "D_POSTGRADE");
    }

    @Override
    public RefRoot listeSOAProfession(String baseUri) throws Exception {
        return processQuery(baseUri, "PROFESSION");
    }

    @Override
    public RefRoot listeSOATypesAnnexes(String baseUri) throws Exception {
        return processQuery(baseUri, "TYPE_PIECE");
    }

    private RefRoot processQuery(String baseUri, String restPath) throws Exception {
        String forgeProgreSOAUrl = this.forgeProgreSOAUrl(baseUri);
        WebTarget target = client.target(forgeProgreSOAUrl);
        target = target.path(DEMAUT_REF_PATH).path(restPath);
        InputStream entity = (InputStream) target.request().get().getEntity();
        String lesAnnexes = IOUtils.toString(entity);
        InputStream lesAnnexesStream = IOUtils.toInputStream(lesAnnexes);
        Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(RefRoot.class).createUnmarshaller();
        return (RefRoot) jaxbUnmarshaller.unmarshal(lesAnnexesStream);
    }

    private String forgeProgreSOAUrl(String serviceBaseUrl) throws Exception {
        if (isAbsolute(progreSOAUrl)) {
            return progreSOAUrl;
        }
        if (isAbsolute(serviceBaseUrl)) {
            return serviceBaseUrl.substring(0, serviceBaseUrl.lastIndexOf("/")).concat(progreSOAUrl);
        }
        throw new Exception("Could not initilize URL Rest Client (ProgreSOA) from " + serviceBaseUrl);
    }

    private boolean isAbsolute(String url) throws URISyntaxException {
        if (StringUtils.isEmpty(url))
            return false;
        return new URI(url).isAbsolute();
    }
}
