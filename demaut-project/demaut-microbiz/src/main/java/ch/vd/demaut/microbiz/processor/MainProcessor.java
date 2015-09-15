package ch.vd.demaut.microbiz.processor;

import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.pee.microbiz.core.rs.MicrobizHeaderConstants;
import ch.vd.pee.microbiz.core.utils.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainProcessor implements Processor {

    public static final String ENVIRONMENT_KEY = "environment";
    public static final String USER_KEY = "user";
    public static final String BUILD_VERSION_KEY = "buildVersion";
    public static final String VERSION_KEY = "version";

    private static final Logger LOGGER = LoggerFactory.getLogger(MainProcessor.class);

    @Value("${demaut.microbiz.environment}")
    private String environment;

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info("MainProcessor");

        ObjectNode response = Json.newObject();

        response.put(ENVIRONMENT_KEY, environment)
                .put(USER_KEY, exchange.getIn().getHeader(MicrobizHeaderConstants.PRINCIPAL_NAME, String.class))
                .put(BUILD_VERSION_KEY, exchange.getIn().getHeader(MicrobizHeaderConstants.COMPONENT_VERSION_AND_DATE, String.class))
                .put(VERSION_KEY, exchange.getIn().getHeader(MicrobizHeaderConstants.COMPONENT_VERSION, String.class));

        Message exchangeOut = exchange.getOut();
        exchangeOut.setBody(response);
        RestUtils.forgeExchangeHeaders(exchangeOut);
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
