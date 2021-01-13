package org.steffen.api.rest.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.steffen.domain.DomainEntity;
import org.steffen.domain.RestErrorInfo;
import org.steffen.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;

/**
 * This class is meant to be extended by all REST resource "controllers".
 * It contains exception mapping and other common REST API functionality
 */
//@ControllerAdvice?
public abstract class AbstractRestHandler implements ApplicationEventPublisherAware
{
    protected static ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    protected static JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();
    protected static SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected ApplicationEventPublisher eventPublisher;

    /**
     * Checks if a DomainEntity Object structure is valid json
     * @param entity - DomainEntity to check json structure
     * @param jsonSchemaFile = json schema file location used to validate entity
     * @return boolean - whether the entity json structure is valid
     */
    public boolean isEntityValidJson(DomainEntity entity, File jsonSchemaFile)
    {
        URI uri = jsonSchemaFile.toURI();

        JsonNode jsonNode = objectMapper.valueToTree(entity);


        try
        {
            ProcessingReport report = jsonSchemaFactory.getJsonSchema(uri.toString()).validate(jsonNode);

            // print validation errors
            if (report.isSuccess())
            {
                System.out.println("no validation errors - json :^)");
                return true;
            }
            else
            {
                report.forEach(processingMessage -> System.out.println(processingMessage.getMessage()));
            }

        }
        catch (ProcessingException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if a DomainEntity Object structure is valid xml
     * @param entity - DomainEntity to check structure
     * @return boolean - whether the entity xml structure is valid
     */
    protected abstract boolean isEntityValidXml(DomainEntity entity);

    public static <T> void checkResourceFound(final T resource)
    {
        if (resource == null)
        {
            throw new ResourceNotFoundException("resource not found");
        }
    }

    // Methods to respond to bad requests

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    public
    @ResponseBody
    RestErrorInfo handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response)
    {
        log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

        return new RestErrorInfo(ex, "You messed up.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public
    @ResponseBody
    RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response)
    {
        log.info("ResourceNotFoundException handler:" + ex.getMessage());

        return new RestErrorInfo(ex, "Sorry I couldn't find it.");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
    {
        this.eventPublisher = applicationEventPublisher;
    }

}
