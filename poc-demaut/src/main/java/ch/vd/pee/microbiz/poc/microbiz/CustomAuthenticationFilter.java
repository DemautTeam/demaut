package ch.vd.pee.microbiz.poc.microbiz;

import org.apache.cxf.jaxrs.security.JAASAuthenticationFilter;

import javax.ws.rs.container.ContainerRequestContext;

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

