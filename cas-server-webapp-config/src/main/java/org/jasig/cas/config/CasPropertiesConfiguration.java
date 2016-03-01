package org.jasig.cas.config;

import org.jasig.cas.CasVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.context.ConfigurableWebEnvironment;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * This is {@link CasPropertiesConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 4.3.0
 */
@Configuration("casPropertiesConfiguration")
public class CasPropertiesConfiguration {
    @Autowired
    private ConfigurableWebEnvironment environment;
    
    /**
     * Place holder configurer property sources placeholder configurer.
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
        
    @PostConstruct
    public void init() {
        final Properties sysProps = System.getProperties();
        final Properties properties = new Properties();
        properties.put("info.cas.version", CasVersion.getVersion());
        properties.put("info.cas.date", CasVersion.getDateTime());
        properties.put("info.cas.java.home", sysProps.get("java.home"));
        properties.put("info.cas.java.vendor", sysProps.get("java.vendor"));
        properties.put("info.cas.java.version", sysProps.get("java.version"));
        final PropertiesPropertySource src = new PropertiesPropertySource(CasVersion.class.getName(), properties);
        environment.getPropertySources().addFirst(src);
    }
}
