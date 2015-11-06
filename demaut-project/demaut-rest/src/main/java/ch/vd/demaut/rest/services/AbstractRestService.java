package ch.vd.demaut.rest.services;

import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.utils.LoginUtils;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

/**
 * Classe de base pour les services Rest
 */
public abstract class AbstractRestService {

    // permet d'accéder aux propriétés HTTP sans polluer les signatures de méthode...
    // nécessite une scope prototype pour éviter des mélanges de context
    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

    protected Login getLogin(){
        return LoginUtils.fetchCurrentUserToken(httpHeaders);
    }

    protected HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    protected UriInfo getUriInfo() {
        return uriInfo;
    }
}
