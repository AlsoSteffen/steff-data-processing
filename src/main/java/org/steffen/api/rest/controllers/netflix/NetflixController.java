package org.steffen.api.rest.controllers.netflix;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.DomainEntity;
import org.steffen.domain.netflix.Netflix;
import org.steffen.services.netflix.NetflixService;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/netflix")
@Api(tags = {"netflix"})
public class NetflixController extends AbstractRestHandler
{
    @Autowired
    NetflixService netflixService;

    private final String requireSpecificStock = "The date of an existing netflix resource";

    private final File jsonSchemaFile = new File("src\\main\\resources\\schema\\netflix\\netflix_schema.json");
    private final File xmlSchemaFile = new File("src\\main\\resources\\schema\\netflix\\netflix_schema.xsd");

    @Override
    protected boolean isEntityValidXml(DomainEntity entity)
    {
        try
        {
            XmlMapper xmlMapper = new XmlMapper();
            String xml;
            xml = "<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<netflixTitles   xmlns=\"https://www.w3schools.com\"\n" +
                    "                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "                 xsi:schemaLocation=\"https://www.w3schools.com netflix_schema.xsd\">";
            xml += xmlMapper.writeValueAsString(entity);
            xml += "</netflixTitles>";

            Schema schema = factory.newSchema(xmlSchemaFile);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));

            System.out.println("No validation errors -xml :D");
            return true;
        }
        catch (IOException | SAXException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    @PostMapping(value = "",
                 consumes = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create a netflix resource")
    public void createNetflix(@RequestBody Netflix netflix)
    {
        if (isEntityValidXml(netflix))
        {
            this.netflixService.createNetflix(netflix);
        }
    }

    @GetMapping(value = "/json/date={date}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single netflix resource based on date")
    public List<Netflix> getJsonNetflixBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                               @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            netflixService.getNetflixTitles(formattedDate).forEach(AbstractRestHandler::checkResourceFound);
            netflixService.getNetflixTitles(formattedDate).forEach(netflix -> isEntityValidJson(netflix, jsonSchemaFile));

            return netflixService.getNetflixTitles(formattedDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/xml/date={date}", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single netflix resource based on date")
    public List<Netflix> getXmlNetflixBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                               @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            netflixService.getNetflixTitles(formattedDate).forEach(AbstractRestHandler::checkResourceFound);
            netflixService.getNetflixTitles(formattedDate).forEach(this::isEntityValidXml);

            return netflixService.getNetflixTitles(formattedDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/json/id={id}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single netflix resource based on id")
    public Netflix getJsonNetflixBasedOnId(@ApiParam(value = requireSpecificStock, required = true)
                                       @PathVariable("id") Long id)
    {
        checkResourceFound(netflixService.getNetflix(id));
        if (isEntityValidJson(netflixService.getNetflix(id), jsonSchemaFile))
        {
            return netflixService.getNetflix(id);
        }
        return null;
    }

    @GetMapping(value = "/xml/id={id}", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single netflix resource based on id")
    public Netflix getXmlNetflixBasedOnId(@ApiParam(value = requireSpecificStock, required = true)
                                      @PathVariable("id") Long id)
    {
        checkResourceFound(netflixService.getNetflix(id));
        if (isEntityValidXml(netflixService.getNetflix(id)))
        {
            return netflixService.getNetflix(id);
        }
        return null;
    }

    @GetMapping(value = "/xml", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all netflix resources")
    public Iterable<Netflix> getNetflixAsXml()
    {
        checkResourceFound(netflixService.getNetflix());
        netflixService.getNetflix().forEach(this::isEntityValidXml);
        return netflixService.getNetflix();
    }

    @GetMapping(value = "/json", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all netflix resources")
    public Iterable<Netflix> getNetflixAsJson()
    {
        checkResourceFound(netflixService.getNetflix());
        netflixService.getNetflix().forEach(netflix -> isEntityValidJson(netflix, jsonSchemaFile));
        return netflixService.getNetflix();
    }

    @PutMapping(value = "/id={id}", consumes = {"application/xml", "application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing netflix resource based on id")
    public void updateNetflix(@ApiParam(value = requireSpecificStock, required = true)
                            @PathVariable("id") Long id,
                            @RequestBody Netflix netflix) throws DataFormatException
    {
        checkResourceFound(netflixService.getNetflix(id));

        if (id != netflix.getId()) throw new DataFormatException("Id does not match!");

        if (isEntityValidXml(netflix) || isEntityValidJson(netflix, jsonSchemaFile))
        {
            this.netflixService.updateNetflix(netflix);
        }
    }

    @PutMapping(value = "/date={date}", consumes = {"application/xml", "application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing netflix resource based on date")
    public void updateNetflix(@ApiParam(value = requireSpecificStock, required = true)
                            @PathVariable("date") String date,
                            @RequestBody Netflix netflix)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            checkResourceFound(netflixService.getNetflix(formattedDate));

            if (isEntityValidXml(netflixService.getNetflix(formattedDate)))
            {
                netflixService.updateNetflix(netflix);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "/id={id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single netflix resource")
    public void deleteNetflix(@ApiParam(value = requireSpecificStock, required = true)
                            @PathVariable("id") Long id)
    {
        checkResourceFound(netflixService.getNetflix(id));
        netflixService.deleteNetflix(id);
    }
}
