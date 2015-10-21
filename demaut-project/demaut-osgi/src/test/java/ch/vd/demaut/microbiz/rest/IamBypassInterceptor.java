package ch.vd.demaut.microbiz.rest;

import ch.vd.pee.microbiz.core.rs.CXFMessageHelper;
import ch.vd.pee.microbiz.core.rs.IamInteceptor;
import ch.vd.pee.microbiz.core.rs.SecurityConstants;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;

public class IamBypassInterceptor extends IamInteceptor {
    @Override
    public void handleMessage(Message message) throws Fault {
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_USERNAME, "xsicrz");
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_APPUSERID, null);
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_APPLICATION, "outils-int_ciad");
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_ROLES, "outils-int_ciad-user");
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_FIRST, "Christophe");
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_LAST, "Rodriguez");
        CXFMessageHelper.setRequestHeaderValue(message, SecurityConstants.IAM_HEADER_EMAIL, "christophe.rodriguez@vd.ch");

        CXFMessageHelper.setRequestHeaderValue(message, CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "GET POST PUT");
        CXFMessageHelper.setRequestHeaderValue(message, CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "false");
        CXFMessageHelper.setRequestHeaderValue(message, CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
        super.handleMessage(message);
    }
}