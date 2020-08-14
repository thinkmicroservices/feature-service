package com.thinkmicroservices.ri.spring.feature.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author cwoodward
 */
@Data
public class FeatureSetRequest {

     
    private String featureSetName;
    
     
    private String applicationClientType;
    
   
    private String accountId;
}
