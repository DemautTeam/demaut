package ch.vd.demaut.microbiz.progreSoa;

import java.io.FileInputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

@Path("/")
public class ProgreSoaServiceMock {

    @GET
    @Path("tiers/${id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response rechercheSOATierById(@PathParam("id") String id) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/tiers_323902038.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("tiers")
    @Produces(MediaType.APPLICATION_XML)
    public Response rechercheSOATierByNom(@QueryParam("nom") String nom) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/tiers_clinique.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/AP_TITRE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOATypesActivites() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/AP_TITRE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/D_FORMATION_APPROFONDIE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAFormationApprofondie() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_FORMATION_APPROFONDIE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/D_FORMATION_COMPLEMENTAIRE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAFormationComplementaire() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_FORMATION_COMPLEMENTAIRE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/D_FORMATION_INITIALE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAFormationInitiale() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_FORMATION_INITIALE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/D_POSTGRADE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOADiplomesPostGrade() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/D_POSTGRADE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/PROFESSION")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOAProfession() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/PROFESSION.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }

    @GET
    @Path("demaut/TYPE_PIECE")
    @Produces(MediaType.APPLICATION_XML)
    public Response listeSOATypesAnnexes() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/TYPE_PIECE.xml");
        return Response.ok(IOUtils.toString(fileInputStream)).build();
    }
}