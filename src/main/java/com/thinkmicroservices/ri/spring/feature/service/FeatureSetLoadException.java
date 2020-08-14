 
package com.thinkmicroservices.ri.spring.feature.service;

import java.io.IOException;

/**
 *
 * @author cwoodward
 */
public class FeatureSetLoadException extends Exception {

    FeatureSetLoadException(String featureSetName, String clientApplicationType, IOException ex) {
       
        super("unable to load featureSet:"+featureSetName+", clientApplication:"+clientApplicationType,ex);
    }
    
}
