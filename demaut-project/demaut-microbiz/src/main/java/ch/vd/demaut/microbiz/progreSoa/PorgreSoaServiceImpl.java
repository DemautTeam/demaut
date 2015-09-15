package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;
import ch.vd.ses.referentiel.tiers_v01.Root;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

@Service
public class PorgreSoaServiceImpl implements PorgreSoaService {

    private Client client = ClientBuilder.newClient();

    private String progreSOAUrl;

    @Autowired
    public PorgreSoaServiceImpl(@Value("${demaut.microbiz.progre.soa.url}") String progreSOAUrl) {
        this.progreSOAUrl = progreSOAUrl;
    }

    @Override
    public List<ReportedOrganisationType> rechercheSOATierById(String organisationId) throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl)) {
            WebTarget target = client.target(progreSOAUrl);
            target = target.path("tiers").queryParam("id", organisationId);
            return target.request().get(Root.class).getMoralPerson();
        } else {
            throw new Exception("Could not initiate Rest Client rechercheSOATierById (url empty)");
        }
    }

    @Override
    public List<ReportedOrganisationType> rechercheSOATierByNom(String organisationNom) throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl)) {
            WebTarget target = client.target(progreSOAUrl);
            target = target.path("tiers").queryParam("nom", organisationNom);
            return target.request().get(Root.class).getMoralPerson();
        } else {
            throw new Exception("Could not initiate Rest Client rechercheSOATierByNom (url empty)");
        }
    }

    @Override
    public RefRoot listeSOAFormationApprofondie() throws Exception {
        return processQuery("D_FORMATION_APPROFONDIE");
    }

    @Override
    public RefRoot listeSOAFormationComplementaire() throws Exception {
        return processQuery("D_FORMATION_COMPLEMENTAIRE");
    }

    @Override
    public RefRoot listeSOAFormationInitiale() throws Exception {
        return processQuery("D_FORMATION_INITIALE");
    }

    @Override
    public RefRoot listeSOADiplomesPostGrade() throws Exception {
        return processQuery("D_POSTGRADE");
    }

    @Override
    public RefRoot listeSOAProfession() throws Exception {
        return processQuery("PROFESSION");
    }

    @Override
    public RefRoot listeSOATypesAnnexes() throws Exception {
        return processQuery("TYPE_PIECE");
    }

    private RefRoot processQuery(String restPath) throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl)) {
            WebTarget target = client.target(progreSOAUrl);
            target = target.path("demaut").path(restPath);
            InputStream entity = (InputStream) target.request().get().getEntity();
            String lesAnnexes = IOUtils.toString(entity);
            InputStream lesAnnexesStream = IOUtils.toInputStream(lesAnnexes);
            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(RefRoot.class).createUnmarshaller();
            return (RefRoot) jaxbUnmarshaller.unmarshal(lesAnnexesStream);
        } else {
            throw new Exception("Could not initiate Rest Client (url empty) " + restPath);
        }
    }
}
