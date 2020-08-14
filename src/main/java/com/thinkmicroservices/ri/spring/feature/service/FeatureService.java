 
package com.thinkmicroservices.ri.spring.feature.service;

 
import java.util.Properties;

/**
 *
 * @author cwoodward
 */
public interface FeatureService {
    
    /**
     * perform a FeatureSet lookup by client type.
     * @param clientApplicationType
     * @param featureSetName
     * @return 
     */
    Properties getFeatureSet(String clientApplicationType, String featureSetName,String accountId) throws FeatureSetLoadException ;
}
