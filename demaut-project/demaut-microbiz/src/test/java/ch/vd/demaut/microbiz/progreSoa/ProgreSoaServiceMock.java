package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.tiers_v01.Root;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Path("/services")
public class ProgreSoaServiceMock {

    @GET
    @Path("recherche/tiers/${id}")
    @Produces(MediaType.APPLICATION_XML)
    @SuppressWarnings("unchecked")
    public Response rechercheSOATierById(@PathParam("id") String id) throws Exception {
        Marshaller jaxbMarshaller = JAXBContext.newInstance(Root.class).createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.marshal(new Root(), stringWriter);
        return Response.ok(stringWriter.toString()).build();
    }

    @GET
    @Path("recherche/tiers/${nom}")
    @Produces(MediaType.APPLICATION_XML)
    @SuppressWarnings("unchecked")
    public Response rechercheSOATierByNom(@PathParam("nom") String nom) throws Exception {
        Marshaller jaxbMarshaller = JAXBContext.newInstance(Root.class).createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.marshal(new Root(), stringWriter);
        return Response.ok(stringWriter.toString()).build();
    }

    @GET
    @Path("annexes/types/liste")
    @Produces(MediaType.APPLICATION_XML)
    @SuppressWarnings("unchecked")
    public Response listerLesTypesAnnexes() throws Exception {
        Marshaller jaxbMarshaller = JAXBContext.newInstance(AnnexetypesList.class).createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        AnnexetypesList annexetypesList = new AnnexetypesList();
        annexetypesList.populate();
        jaxbMarshaller.marshal(annexetypesList, stringWriter);
        return Response.ok(stringWriter.toString()).build();
    }
}
