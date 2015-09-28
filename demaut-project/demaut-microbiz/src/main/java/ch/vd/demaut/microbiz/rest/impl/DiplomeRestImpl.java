package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.PaysNotFoundException;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.DiplomeRest;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowOrigins = {"*"}, allowCredentials = true, maxAge = 3600, allowHeaders = {
        "Content-Type", "X-Requested-With"}, exposeHeaders = {"Access-Control-Allow-Origin"})
@Service("diplomeRestImpl")
@Path("/diplomes")
public class DiplomeRestImpl implements DiplomeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiplomeRestImpl.class);

    @Inject
    private ProgreSoaService progreSoaService;

    @Override
    @GET
    @Path("/typeDiplomesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesDeDiplomes(@Context UriInfo uriInfo) throws Exception {

        LOGGER.info("listerLesTypesDeDiplomes");

        // Altrenative:
        List<TypeDiplomeAccepte> diplomeAcceptes = buildListeTypesDiplomesAcceptesSansProgresSOA();
        // Autre altrenative:
        //List<VcType> diplomeAcceptes = buildListeTypesDiplomesAcceptesAvecProgresSOA(uriInfo);
        return RestUtils.forgeResponseList(diplomeAcceptes);
    }

    private List<TypeDiplomeAccepte> buildListeTypesDiplomesAcceptesSansProgresSOA() {
        return Arrays.asList(TypeDiplomeAccepte.values());
    }

    private List<TypeDiplomeAccepte> buildListeTypesDiplomesAcceptesAvecProgresSOA(UriInfo uriInfo) {
        throw new TypeDiplomeNotFoundException();
    }

    @Override
    @GET
    @Path("/typeFormationsList/{typeDiplome}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTitresFormations(@Context UriInfo uriInfo, @PathParam("typeDiplome") String typeDiplomeId)
            throws Exception {

        LOGGER.info("listerLesTitresFormations " + typeDiplomeId);

        TypeDiplomeAccepte typeDiplomeAccepte = TypeDiplomeAccepte.getTypeById(Integer.parseInt(typeDiplomeId));

        // Altrenative:
        List<?> titreFormations = buildListeTitresFormationsSansProgresSOA(typeDiplomeAccepte);
        // Autre altrenative:
        //List<VcType> titreFormations = buildListeTitresFormationsAvecProgresSOA(uriInfo, typeDiplomeAccepte);
        return RestUtils.forgeResponseList(titreFormations);
    }

    private List<?> buildListeTitresFormationsSansProgresSOA(TypeDiplomeAccepte typeDiplomeAccepte) {
        switch (typeDiplomeAccepte) {
            case D_FORMATION_APPROFONDIE:
                return Arrays.asList(TitreFormationApprofondieProgres.values());
            case D_FORMATION_COMPLEMENTAIRE:
                return Arrays.asList(TitreFormationComplementaireProgres.values());
            case D_FORMATION_INITIALE:
                return Arrays.asList(TitreFormationInitialeProgres.values());
            case D_POSTGRADE:
                return Arrays.asList(TitreFormationPostgradeProgres.values());
            default:
                return new ArrayList<>();
        }
    }

    private List<VcType> buildListeTitresFormationsAvecProgresSOA(UriInfo uriInfo, TypeDiplomeAccepte typeDiplomeAccepte) throws Exception {
        String path = uriInfo != null ? uriInfo.getBaseUri().toString() : null;
        switch (typeDiplomeAccepte) {
            case D_FORMATION_APPROFONDIE:
                return progreSoaService.listeSOADiplomesFormationApprofondie(path).getVcList().getVc();
            case D_FORMATION_COMPLEMENTAIRE:
                return progreSoaService.listeSOADiplomesFormationComplementaire(path).getVcList().getVc();
            case D_FORMATION_INITIALE:
                return progreSoaService.listeSOADiplomesFormationInitiale(path).getVcList().getVc();
            case D_POSTGRADE:
                return progreSoaService.listeSOADiplomesFormationPostGrade(path).getVcList().getVc();
            default:
                return new ArrayList<>();
        }
    }

    @Override
    @GET
    @Path("/paysList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesPays(@Context UriInfo uriInfo) throws Exception {

        LOGGER.info("listerLesPays");

        // Altrenative:
        List<Pays> paysList = buildListePaysSansProgresSOA();
        // Autre altrenative:
        //List<VcType> paysList = buildListePaysAvecProgresSOA(uriInfo);
        return RestUtils.forgeResponseList(paysList);
    }

    private List<Pays> buildListePaysSansProgresSOA() {
        return Arrays.asList(Pays.values());
    }

    private List<VcType> buildListePaysAvecProgresSOA(UriInfo uriInfo) {
        throw new PaysNotFoundException();
    }
}
