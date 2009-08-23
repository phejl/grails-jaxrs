/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.jaxrs.provider;

import static org.grails.jaxrs.provider.ConverterUtils.getDefaultEncoding;
import static org.grails.jaxrs.provider.ConverterUtils.xmlToMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware;
import org.grails.jaxrs.support.MessageBodyReaderSupport;

/**
 * @author Martin Krasser
 */
@Provider
@Consumes("text/xml")
public class XMLReader extends MessageBodyReaderSupport<Map> implements GrailsApplicationAware {

    private GrailsApplication grailsApplication;
    
    @Override
    public void setGrailsApplication(GrailsApplication grailsApplication) {
        this.grailsApplication = grailsApplication;
    }

    @Override
    public Map readFrom(MultivaluedMap<String, String> httpHeaders, InputStream entityStream) 
        throws IOException, WebApplicationException {
        
        // TODO: obtain encoding from HTTP header and/or XML document
        String encoding = getDefaultEncoding(grailsApplication);

        // Convert XML to map used for constructing domain objects
        return xmlToMap(entityStream, encoding);
    }

}