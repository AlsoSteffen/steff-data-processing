package org.steffen.api.rest.controllers.tesla;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.DomainEntity;
import org.steffen.domain.tesla.TeslaStock;
import org.steffen.services.tesla.TeslaStockService;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/teslaStock")
@Api(tags = {"teslaStock"})
public class TeslaStockController extends AbstractRestHandler
{
    @Autowired
    TeslaStockService teslaStockService;

    private final String requireSpecificStock = "The date of an existing teslaStock resource";

    private final File jsonSchemaFile = new File("src\\main\\resources\\schema\\tesla\\tesla_schema.json");
    private final File xmlSchemaFile = new File("src\\main\\resources\\schema\\tesla\\tesla_schema.xsd");

    /**
     * see - AbstractRestHandler.isEntityValidXml
     * @param entity - DomainEntity to check structure
     * @return boolean - whether the xml is valid or not
     */
    @Override
    protected boolean isEntityValidXml(DomainEntity entity)
    {
        try
        {
            XmlMapper xmlMapper = new XmlMapper();
            String xml;
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<teslaStocks xmlns=\"https://www.w3schools.com\"\n" +
                    "             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "             xsi:schemaLocation=\"https://www.w3schools.com tesla_schema.xsd\">";
            xml += xmlMapper.writeValueAsString(entity);
            xml += "</teslaStocks>";

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
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

    /**
     * Creating this object needs the date format of MM/dd/yyyy
     * because of the date format in the dataset
     * @param teslaStock - TeslaStock Object created from the input
     */
    @PostMapping(value = "",
                 consumes = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create a teslaStock resource")
    public void createTeslaStock(@RequestBody TeslaStock teslaStock)
    {
        if (isEntityValidXml(teslaStock) || isEntityValidJson(teslaStock, jsonSchemaFile))
        {
            this.teslaStockService.createTeslaStock(teslaStock);
        }
    }

    @GetMapping(value = "/json/date={date}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single teslaStock resource based on date")
    public Iterable<TeslaStock> getJsonTeslaStockBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                                   @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            List<TeslaStock> validStocks = new ArrayList<>();

            checkResourceFound(teslaStockService.getTeslaStocks(formattedDate));
            teslaStockService.getTeslaStocks(formattedDate).forEach(teslaStock -> isEntityValidJson(teslaStock, jsonSchemaFile));
            for (TeslaStock teslaStock : teslaStockService.getTeslaStocks(formattedDate))
            {
                if (isEntityValidJson(teslaStock, jsonSchemaFile))
                {
                    validStocks.add(teslaStock);
                }
            }
            return validStocks;
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    @GetMapping(value = "/xml/date={date}", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single teslaStock resource based on date")
    public Iterable<TeslaStock> getXmlTeslaStockBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                                  @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            List<TeslaStock> validStocks = new ArrayList<>();

            checkResourceFound(teslaStockService.getTeslaStocks(formattedDate));
            teslaStockService.getTeslaStocks(formattedDate).forEach(this::isEntityValidXml);
            for (TeslaStock teslaStock : teslaStockService.getTeslaStocks(formattedDate))
            {
                if (isEntityValidXml(teslaStock))
                {
                    validStocks.add(teslaStock);
                }
            }
            return validStocks;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/xml", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all teslaStock resources")
    public Iterable<TeslaStock> getTeslaStocksAsXml()
    {
        List<TeslaStock> validStocks = new ArrayList<>();

        checkResourceFound(teslaStockService.getTeslaStocks());
        teslaStockService.getTeslaStocks().forEach(this::isEntityValidXml);
        for (TeslaStock teslaStock : teslaStockService.getTeslaStocks())
        {
            if (isEntityValidXml(teslaStock))
            {
                validStocks.add(teslaStock);
            }
        }
        return validStocks;
    }

    @GetMapping(value = {"","/json"}, produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all teslaStock resources")
    public Iterable<TeslaStock> getTeslaStocksAsJson()
    {
        List<TeslaStock> validStocks = new ArrayList<>();

        checkResourceFound(teslaStockService.getTeslaStocks());
        teslaStockService.getTeslaStocks().forEach(teslaStock -> isEntityValidJson(teslaStock, jsonSchemaFile));
        for (TeslaStock teslaStock : teslaStockService.getTeslaStocks())
        {
            if (isEntityValidJson(teslaStock, jsonSchemaFile))
            {
                validStocks.add(teslaStock);
            }
        }
        return validStocks;
    }

    @PutMapping(value = "/{date}", consumes = {"application/xml", "application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing teslaStock resource")
    public void updateTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("date") Date date,
                                 @RequestBody TeslaStock teslaStock) throws DataFormatException
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));

        if (date != teslaStock.getDate()) throw new DataFormatException("Date does not match!");

        if (isEntityValidXml(teslaStock) || isEntityValidJson(teslaStock, jsonSchemaFile))
        {
            this.teslaStockService.updateTeslaStock(teslaStock);
        }
    }

    @DeleteMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single teslaStock resource")
    public void deleteTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("date") Date date)
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));
        teslaStockService.deleteTeslaStock(date);
    }
}
