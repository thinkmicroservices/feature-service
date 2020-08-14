package com.thinkmicroservices.ri.spring.feature.controller;

import com.thinkmicroservices.ri.spring.feature.model.FeatureSetRequest;
import com.thinkmicroservices.ri.spring.feature.service.FeatureService;
import com.thinkmicroservices.ri.spring.feature.service.FeatureSetLoadException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cwoodward
 */
@RestController
@Slf4j
public class FeatureController {

    @Value("${client.logging.level:INFO}")
    private String loggingLevel;

    @Autowired
    private FeatureService featureService;
    @Autowired
    private MeterRegistry meterRegistry;

    private Counter featureSetRequestCounter;

    @RequestMapping(value = "/lookup", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header",defaultValue="bearer <token-value>")})
   @ApiOperation(value = "Lookup Feature Set",
  notes = "Performs a feature set lookup.")
    public Properties lookupFeatureSet(FeatureSetRequest featureSetRequest) throws FeatureSetLoadException {

        log.debug("lookup feature set[{}] for client [{}]", featureSetRequest.getFeatureSetName(), featureSetRequest.getApplicationClientType(), featureSetRequest.getAccountId());
        Properties featureSetProperties = featureService.getFeatureSet(featureSetRequest.getFeatureSetName(), featureSetRequest.getApplicationClientType(), featureSetRequest.getAccountId());
        log.debug("feature set:" + featureSetProperties);
        featureSetRequestCounter.increment();
        return featureSetProperties;
    }

    /**
     * initialize the service metrics
     */
    @PostConstruct
    private void initializeMetrics() {
        featureSetRequestCounter = Counter.builder("feature.set.request")
                .description("The number of feature set requests.")
                .register(meterRegistry);

    }
}
