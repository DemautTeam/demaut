package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.commons.json.RestUtils;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("personelRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/personal")
public class PersonelRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonelRestImpl.class);

    @Autowired
    private DonneesProfessionnellesService donneesProfessionnellesService;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/nationalites")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesNationalites() throws Exception {

        LOGGER.info("listerLesNationalites");

        // Altrenative:
        List<Pays> paysList = buildListePaysSansProgresSOA();
        // Autre altrenative:
        //List<VcType> paysList = buildListePaysAvecProgresSOA(uriInfo);
        return RestUtils.buildRef(paysList);
    }

    private List<Pays> buildListePaysSansProgresSOA() {
        return Arrays.asList(Pays.values());
    }

    @GET
    @Path("/langues")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesLangues() throws Exception {

        LOGGER.info("listerLesLangues");

        // Altrenative:
        List<Langue> paysList = buildListeLangueSansProgresSOA();
        // Autre altrenative:
        //List<VcType> paysList = buildListeLangueAvecProgresSOA(uriInfo);
        return RestUtils.buildRef(paysList);
    }

    private List<Langue> buildListeLangueSansProgresSOA() {
        return Arrays.asList(Langue.values());
    }

    /**
     * Méthode qui renseigner les Donnees Personnelles du demandeur
     * dateDeNaissance String (format 2015-10-06T22:00:00.000Z)
     */
    @SuppressWarnings("all")
    @GET
    @Path("/ajouter")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response renseignerLesDonneesPersonnelles(@QueryParam("nom") String nomStr,
                                                     @QueryParam("prenom") String prenomStr,
                                                     @QueryParam("nomDeCelibataire") String nomDeCelibataireStr,
                                                     @QueryParam("adressePersonnelle") String adressePersonnelle,
                                                     @QueryParam("complement") String complement,
                                                     @QueryParam("localite") String localiteStr,
                                                     @QueryParam("npa") String npaStr,
                                                     @QueryParam("pays") String paysId,
                                                     @QueryParam("telephonePrive") String telephonePriveStr,
                                                     @QueryParam("telephoneMobile") String telephoneMobileStr,
                                                     @QueryParam("email") String emailStr,
                                                     @QueryParam("fax") String faxStr,
                                                     @QueryParam("genre") String genreStr,
                                                     @QueryParam("dateDeNaissance") String dateDeNaissanceStr,
                                                     @QueryParam("nationalite") String nationaliteId,
                                                     @QueryParam("langue") String langueId,
                                                     @QueryParam("permis") String permisStr,
                                                     @QueryParam("permisOther") String permisOther) throws Exception {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("ajouterUnDiplome pour : " + login.getValue() + ", nom=" + nomStr + ", prenom=" + prenomStr);

        Nom nom = new Nom(nomStr);
        Prenom prenom = new Prenom(prenomStr);
        NomDeCelibataire nomDeCelibataire = new NomDeCelibataire(nomDeCelibataireStr);
        Localite localite = new Localite(localiteStr);
        NPA npa = new NPA(npaStr);
        Pays pays = Pays.getTypeById(Integer.parseInt(paysId));
        Adresse adresse = new Adresse(adressePersonnelle, complement, localite, npa, pays);
        Email email = new Email(emailStr);
        NumeroTelephone telephonePrive = new NumeroTelephone(telephonePriveStr);
        NumeroTelephone telephoneMobile = new NumeroTelephone(telephoneMobileStr);
        NumeroTelephone fax = new NumeroTelephone(faxStr);
        Genre genre = Genre.valueOf(genreStr);
        DateDeNaissance dateDeNaissance = new DateDeNaissance(DiplomeRestImpl.SHORT_DATE_PARSER.parseLocalDate(dateDeNaissanceStr));
        Pays nationalite = Pays.getTypeById(Integer.parseInt(nationaliteId));
        Langue langue = Langue.getTypeById(Integer.parseInt(langueId));
        Permis permis = null;
        if (!Pays.Suisse.equals(nationalite)) {
            if (StringUtils.isEmpty(permisStr)) {
                AutrePermis autrePermis = new AutrePermis(permisOther);
                permis = new Permis(autrePermis);
            } else {
                TypePermis typePermis = TypePermis.valueOf(permisStr);
                permis = new Permis(typePermis);
            }
        }

        donneesProfessionnellesService.renseignerLesDonneesPersonnelles(login, nom, prenom, nomDeCelibataire, localite, npa, pays, adresse, email,
                telephonePrive, telephoneMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);

        return RestUtils.buildJSon(Arrays.asList(true));
    }
}
