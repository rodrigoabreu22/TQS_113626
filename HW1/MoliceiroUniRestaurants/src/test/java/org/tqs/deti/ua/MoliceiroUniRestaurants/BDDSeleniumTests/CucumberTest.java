package org.tqs.deti.ua.MoliceiroUniRestaurants.BDDSeleniumTests;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("org/tqs/deti/ua/MoliceiroUniRestaurants")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.tqs.deti.ua.MoliceiroUniRestaurants")
public class CucumberTest {
    
}