package ch.vd.demaut.microbiz.progreSoa.impl;

import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;
import ch.vd.ses.referentiel.tiers_v01.Root;
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

    @Value("${demaut.microbiz.progre.soa.endpoint}")
    private String progreSOAUrl;

    @Value("${demaut.microbiz.service.endpoint}")
    private String demautMicrobizUrl;

    @Override
    public List<ReportedOrganisationType> rechercheSOATierById(String baseUri, String organisationId) throws Exception {
        String forgeProgreSOAUrl = this.forgeProgreSOAUrl(baseUri);
        if (!StringUtils.isEmpty(forgeProgreSOAUrl)) {
            WebTarget target = client.target(forgeProgreSOAUrl);
            target = target.path(DEMAUT_TIERS_PATH).queryParam("id", organisationId);
            return target.request().get(Root.class).getMoralPerson();
        } else {
            throw new Exception("Could not initiate Rest Client rechercheSOATierById (url empty)");
        }
    }

    @Override
    public List<ReportedOrganisationType> rechercheSOATierByNom(String baseUri, String organisationNom) throws Exception {
        String forgeProgreSOAUrl = this.forgeProgreSOAUrl(baseUri);
        if (!StringUtils.isEmpty(forgeProgreSOAUrl)) {
            WebTarget target = client.target(forgeProgreSOAUrl);
            target = target.path(DEMAUT_TIERS_PATH).queryParam("nom", organisationNom);
            return target.request().get(Root.class).getMoralPerson();
        } else {
            throw new Exception("Could not initiate Rest Client rechercheSOATierByNom (url empty)");
        }
    }

    @Override
    public RefRoot listeSOAAPTitre(String baseUri) throws Exception {
        return processQuery(baseUri, "AP_TITRE");
    }

    public RefRoot listeSOAFormationApprofondie(String baseUri) throws Exception {
        return processQuery(baseUri, "D_FORMATION_APPROFONDIE");
    }

    @Override
    public RefRoot listeSOAFormationComplementaire(String baseUri) throws Exception {
        return processQuery(baseUri, "D_FORMATION_COMPLEMENTAIRE");
    }

    @Override
    public RefRoot listeSOAFormationInitiale(String baseUri) throws Exception {
        return processQuery(baseUri, "D_FORMATION_INITIALE");
    }

    @Override
    public RefRoot listeSOADiplomesPostGrade(String baseUri) throws Exception {
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
        if (!StringUtils.isEmpty(forgeProgreSOAUrl)) {
            WebTarget target = client.target(forgeProgreSOAUrl);
            target = target.path(DEMAUT_REF_PATH).path(restPath);
            InputStream entity = (InputStream) target.request().get().getEntity();
            String lesAnnexes = IOUtils.toString(entity);
            InputStream lesAnnexesStream = IOUtils.toInputStream(lesAnnexes);
            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(RefRoot.class).createUnmarshaller();
            return (RefRoot) jaxbUnmarshaller.unmarshal(lesAnnexesStream);
        } else {
            throw new Exception("Could not initiate Rest Client (url empty) " + restPath);
        }
    }

    private String forgeProgreSOAUrl(String serviceBaseUrl) throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl) && new URI(progreSOAUrl).isAbsolute()) {
            return progreSOAUrl;
        } else {
            if (!StringUtils.isEmpty(serviceBaseUrl) && new URI(serviceBaseUrl).isAbsolute()) {
                return serviceBaseUrl.substring(0, serviceBaseUrl.lastIndexOf("/")).concat(progreSOAUrl);
            } else {
                if (!StringUtils.isEmpty(demautMicrobizUrl) && new URI(demautMicrobizUrl).isAbsolute()) {
                    return demautMicrobizUrl.substring(0, demautMicrobizUrl.indexOf("/")).concat(progreSOAUrl);
                } else {
                    throw new Exception("Could not initilize URL Rest Client (ProgreSOA) from " + serviceBaseUrl);
                }
            }
        }
    }
}
