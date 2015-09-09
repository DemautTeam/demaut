package ch.vd.demaut.microbiz.progreSoa;

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

@Service
public class PorgreSoaService {

    private Client client = ClientBuilder.newClient();

    private String progreSOAUrl;

    @Autowired
    public PorgreSoaService(@Value("${demaut.microbiz.progre.soa.url}") String progreSOAUrl) {
        this.progreSOAUrl = progreSOAUrl;
    }

    public Root rechercheSOATierById(String organisationId) throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl)) {
            WebTarget target = client.target(progreSOAUrl);
            target = target.path("services").path("recherche").path("tiers").path(organisationId);
            return target.request().get(Root.class);
        } else {
            throw new Exception("Could not initiate Rest Client rechercheSOATierById (url empty)");
        }
    }

    public Root rechercheSOATierByNom(String organisationNom) throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl)) {
            WebTarget target = client.target(progreSOAUrl);
            target = target.path("services").path("recherche").path("tiers").queryParam("nom", organisationNom);
            return target.request().get(Root.class);
        } else {
            throw new Exception("Could not initiate Rest Client rechercheSOATierByNom (url empty)");
        }
    }

    @SuppressWarnings("all")
    public AnnexetypesList listerLesTypesAnnexes() throws Exception {
        if (!StringUtils.isEmpty(progreSOAUrl)) {
            WebTarget target = client.target(progreSOAUrl);
            target = target.path("services").path("annexes").path("types").path("liste");
            InputStream entity = (InputStream) target.request().get().getEntity();
            String lesAnnexes = IOUtils.toString(entity);
            InputStream lesAnnexesStream = IOUtils.toInputStream(lesAnnexes);
            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(AnnexetypesList.class).createUnmarshaller();
            return (AnnexetypesList) jaxbUnmarshaller.unmarshal(lesAnnexesStream);
        } else {
            throw new Exception("Could not initiate Rest Client listerLesTypesAnnexes (url empty)");
        }
    }
}
