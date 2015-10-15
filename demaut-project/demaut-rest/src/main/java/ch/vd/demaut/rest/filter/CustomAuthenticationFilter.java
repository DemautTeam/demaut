package ch.vd.demaut.rest.filter;

import org.apache.cxf.jaxrs.security.JAASAuthenticationFilter;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * Permet de désactiver le filtre authentication
 * TODO: Penser à l'activer en UAT(QUALITE) / INTG / PROD
 */
public class CustomAuthenticationFilter extends JAASAuthenticationFilter {

    private boolean active;

    @Override
    public void filter(ContainerRequestContext context) {
        if (active) {
            super.filter(context);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

