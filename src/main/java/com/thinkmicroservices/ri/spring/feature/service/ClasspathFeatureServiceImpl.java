package com.thinkmicroservices.ri.spring.feature.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author cwoodward
 */
@Service
@Slf4j
public class ClasspathFeatureServiceImpl implements FeatureService {

    private static final String CLASSPATH_PROPERTIES_PATH_PREFIX = "feature-sets/";
    private static final String CLASSPATH_PROPERTY_FILE_EXTENSION = ".properties";
    private static final String CLIENT_TYPE_DEFAULT = "DEFAULT";
    private static final String FEATURE_SET_NAME_DEFAULT = "DEFAULT";
    @Autowired
    private MeterRegistry meterRegistry;

    private Counter featureSetRequestCounter;

    @Override
    public Properties getFeatureSet(String clientApplicationType, String featureSetName, String accountId) throws FeatureSetLoadException {
        try {
            // use default if not set
            if (clientApplicationType == null) {
                clientApplicationType = CLIENT_TYPE_DEFAULT;
            }
            // use default if not set
            if (featureSetName == null) {
                featureSetName = FEATURE_SET_NAME_DEFAULT;
            }
            Properties featureSetProperties = loadClasspathResourceAsProperties(CLASSPATH_PROPERTIES_PATH_PREFIX + featureSetName + "/" + clientApplicationType + CLASSPATH_PROPERTY_FILE_EXTENSION);
            featureSetRequestCounter.increment();
            return featureSetProperties;
        } catch (IOException ex) {
            throw new FeatureSetLoadException(featureSetName, clientApplicationType, ex);
        }
    }

    /**
     * reads a classpath resource into a Properties instance these files are
     * located in the 'featuresets' classpath
     *
     * @param classpathFile
     * @return
     */
    private Properties loadClasspathResourceAsProperties(String classpathResource) throws IOException {
        log.debug("loading classpathResource:{}", classpathResource);
        Resource resource = new ClassPathResource(classpathResource);
        log.debug("resource:{}", resource);
        Properties properties = new Properties();
        log.debug("properties:{}", properties);
        properties.load(resource.getInputStream());
        log.debug("properties:{}", properties);
        return properties;

    }

    /**
     * initialize the service metrics
     */
    @PostConstruct
    private void initializeMetrics() {
        featureSetRequestCounter = Counter.builder("feature.set.lookup.success")
                .description("The number of successful feature set lookups.")
                .register(meterRegistry);

    }
}
