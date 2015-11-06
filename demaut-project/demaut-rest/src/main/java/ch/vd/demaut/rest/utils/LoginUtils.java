package ch.vd.demaut.rest.utils;

import ch.vd.demaut.domain.exception.UtilisateurNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.HttpHeaders;

/**
 * Cette class permet de créer l'utilisateur grâce au context applicatif.
 * <p/>
 * Elle utilise le header spécifique
 */
public class LoginUtils {

    /**
     * Header injecté par la plateforme cyber pour appeler les services (TBD)
     */
    public static final String DEMAUT_USER_ID_HEADER = "demaut-user-id";
    /**
     * Header IAM "normal"
     */
    public static final String IAM_USER_ID_HEADER = "iam-user-id";

    public static Login fetchCurrentUserToken(HttpHeaders httpHeaders) {
        String userToken = httpHeaders.getHeaderString(DEMAUT_USER_ID_HEADER);
        if (StringUtils.isEmpty(userToken)) {
            throw new UtilisateurNotFoundException();
        }
        return new Login(userToken);
    }
}
