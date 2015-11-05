package ch.vd.demaut.microbiz.rest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;

import ch.vd.pee.microbiz.core.rs.CXFMessageHelper;
import ch.vd.pee.microbiz.core.rs.IamInteceptor;

/**
 * Allow cross origin requests
 * 
 * TODO: remove this class before productionn
 *
 */
public class IamBypassInterceptor extends IamInteceptor {
    @Override
    public void handleMessage(Message message) throws Fault {
        CXFMessageHelper.setRequestHeaderValue(message, CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");

        super.handleMessage(message);
    }
}