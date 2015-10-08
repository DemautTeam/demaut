package ch.vd.ses.demaut.portail.service.impl;

import ch.vd.ses.demaut.portail.service.ConfiguratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 *
 */
@Service("configuratorService")
public class ConfiguratorServiceImpl implements ConfiguratorService{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${baseMicrobiz}")
    private String urlPrefix;

    @Override
    public String getUrlPrefix() {
        return "{\"urlPrefix\":\"" + urlPrefix + "\"}";
    }
}
