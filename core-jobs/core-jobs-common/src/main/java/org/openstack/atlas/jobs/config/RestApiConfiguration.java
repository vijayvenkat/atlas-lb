package org.openstack.atlas.jobs.config;

import org.openstack.atlas.common.config.osgi.cfg.commons.ApacheCommonsConfiguration;
import org.springframework.stereotype.Component;

@Component
public class RestApiConfiguration extends ApacheCommonsConfiguration {
    public static final String defaultConfigurationLocation = "e:/etc/openstack/atlas/public-api.conf";

    public RestApiConfiguration() {
        super(defaultConfigurationLocation);
    }
}
