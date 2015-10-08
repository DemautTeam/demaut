package ch.vd.demaut.progreSoa.services.mock;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import ch.vd.demaut.progreSoa.services.impl.ProgreSoaServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;

@Service("progreSoaMock")
@Path("/")
public class ProgreSoaMock {

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_TIERS_PATH + "/${id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response rechercheSOATierById(@PathParam("id") String organisationId) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/tiers_323902038.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_TIERS_PATH)
    @Produces(MediaType.APPLICATION_XML)
    public Response rechercheSOATierByNom(@QueryParam("nom") String organisationNom) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/tiers_clinique.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/AP_TITRE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOATypesActivites() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/AP_TITRE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/D_FORMATION_APPROFONDIE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAFormationApprofondie() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_FORMATION_APPROFONDIE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/D_FORMATION_COMPLEMENTAIRE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAFormationComplementaire() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_FORMATION_COMPLEMENTAIRE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/D_FORMATION_INITIALE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAFormationInitiale() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_FORMATION_INITIALE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/D_POSTGRADE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOADiplomesPostGrade() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_POSTGRADE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/PROFESSION")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAProfession() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/PROFESSION.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path(ProgreSoaServiceImpl.DEMAUT_REF_PATH + "/TYPE_PIECE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOATypesAnnexes() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/TYPE_PIECE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }
}
