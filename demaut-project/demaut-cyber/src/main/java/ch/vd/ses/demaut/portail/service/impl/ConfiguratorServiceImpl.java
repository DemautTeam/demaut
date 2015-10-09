package ch.vd.ses.demaut.portail.service.impl;

import ch.vd.ses.demaut.portail.service.ConfiguratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service("configuratorService")
public class ConfiguratorServiceImpl implements ConfiguratorService {

    @Value("${baseMicrobiz}")
    private String urlPrefix;

    @Override
    public String getUrlPrefix() {
        return "{\"urlPrefix\":\"" + urlPrefix + "\"}";
    }
}
