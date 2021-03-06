package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.AbstractRestService;
import ch.vd.demaut.services.demandeurs.donneesPerso.DonneesPersonnellesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.io.IOException;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/personal")
public class PersonnelRestImpl extends AbstractRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonnelRestImpl.class);

    private DonneesPersonnellesService donneesPersonnellesService;

    public void setDonneesPersonnellesService(DonneesPersonnellesService donneesPersonnellesService) {
        this.donneesPersonnellesService = donneesPersonnellesService;
    }

    /**
     * Méthode qui renseigner les Donnees Personnelles du demandeur
     *
     */
    @SuppressWarnings("all")
    @GET
    @Path("/renseigner")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response renseignerLesDonneesPersonnelles(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
            @QueryParam("nom") String nomStr, @QueryParam("prenom") String prenomStr,
            @QueryParam("nomDeCelibataire") String nomDeCelibataireStr,
            @QueryParam("adressePersonnelle") String adressePersonnelle, @QueryParam("complement") String complement,
            @QueryParam("localite") String localiteStr, @QueryParam("npa") String npaStr,
            @QueryParam("pays") Integer paysId, @QueryParam("telephonePrive") String telephonePriveStr,
            @QueryParam("telephoneMobile") String telephoneMobileStr, @QueryParam("email") String emailStr,
            @QueryParam("fax") String faxStr, @QueryParam("genre") String genreStr,
            @QueryParam("dateDeNaissance") String dateDeNaissanceStr, @QueryParam("nationalite") Integer nationaliteId,
            @QueryParam("langue") String langueStr, @QueryParam("typePermis") String typePermisStr,
            @QueryParam("autrePermis") String autrePermisStr) throws Exception {

        Login login = getLogin();

        LOGGER.info("renseignerLesDonneesPersonnelles pour : " + login.getValue() + ", referenceDeDemande="
                + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        Nom nom = new Nom(nomStr);
        Prenom prenom = new Prenom(prenomStr);
        Nom nomDeCelibataire = StringUtils.isEmpty(nomDeCelibataireStr)? null : new Nom(nomDeCelibataireStr);
        Localite localite = new Localite(localiteStr);
        NPA npa = new NPA(npaStr);
        Pays pays = Pays.getTypeById(paysId);
        Adresse adresse = new Adresse(adressePersonnelle, complement, localite, npa, pays);
        Email email = new Email(emailStr);
        Telephone telephonePrive = StringUtils.isEmpty(telephonePriveStr) ? null : new Telephone(telephonePriveStr);
        Telephone telephoneMobile = StringUtils.isEmpty(telephoneMobileStr) ? null : new Telephone(telephoneMobileStr);
        Telephone fax = StringUtils.isEmpty(faxStr) ? null : new Telephone(faxStr);
        Genre genre = Genre.valueOf(genreStr);
        DateDeNaissance dateDeNaissance = new DateDeNaissance(
                DiplomeRestImpl.SHORT_DATE_PARSER.parseLocalDate(dateDeNaissanceStr));
        Pays nationalite = Pays.getTypeById(nationaliteId);
        Langue langue = Langue.valueOf(langueStr);
        
        //Construction de Permis
        Permis permis = null;
        TypePermis typePermis = StringUtils.isEmpty(typePermisStr)? null : TypePermis.valueOf(typePermisStr);
        AutrePermis autrePermis = StringUtils.isEmpty(autrePermisStr)? null : new AutrePermis(autrePermisStr);
        if (typePermis != null) {
            permis = new Permis(typePermis, autrePermis);
        }
        
        donneesPersonnellesService.renseignerLesDonneesPersonnelles(login, referenceDeDemande, nom, prenom,
                nomDeCelibataire, adresse, email, telephonePrive, telephoneMobile, fax, genre, dateDeNaissance,
                nationalite, langue, permis);

        return RestUtils.buildJSonResponse(true);
    }

    /**
     * Méthode qui renvoie un broullion de donnees personnelles via referenceDeDemande
     */
    @GET
    @Path("/recuperer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererDonneesPersonnelles(@QueryParam("referenceDeDemande") String referenceDeDemandeStr)
            throws IOException {

        Login login = getLogin();

        LOGGER.info("recuperer Brouillon de donnees personnelles pour : " + login.getValue() + ", referenceDeDemande="
                + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        DonneesPersonnelles donneesPersonnelles = donneesPersonnellesService.recupererDonneesPersonnelles(login,
                referenceDeDemande);

        return RestUtils.buildJSonResponse(donneesPersonnelles);
    }
}
