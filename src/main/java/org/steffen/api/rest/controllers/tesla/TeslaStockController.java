package org.steffen.api.rest.controllers.tesla;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/teslaStock")
@Api(tags = {"teslaStock"})
public class TeslaStockController extends AbstractRestHandler
{
    @Autowired
    TeslaStockService teslaStockService;

    private final String requireSpecificStock = "The date of an existing teslaStock resource";

    private boolean isTeslaStockValidJson(TeslaStock teslaStock)
    {
        ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();
        try
        {
            File jsonSchemaFile = new File("src\\main\\resources\\schema\\tesla\\tesla_schema.json");
            URI uri = jsonSchemaFile.toURI();

            JsonNode jsonNode = objectMapper.valueToTree(teslaStock);
            JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(uri.toString());
            ProcessingReport validationResult = jsonSchema.validate(jsonNode);

            // print validation errors
            if (validationResult.isSuccess())
            {
                System.out.println("no validation errors - json :^)");
                return true;
            }
            else
            {
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            }
        }
        catch (ProcessingException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isTeslaStockValidXml(TeslaStock teslaStock)
    {
        try
        {
            XmlMapper xmlMapper = new XmlMapper();
            String xml;
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<teslaStocks xmlns=\"https://www.w3schools.com\"\n" +
                    "             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "             xsi:schemaLocation=\"https://www.w3schools.com tesla_schema.xsd\">";
            xml += xmlMapper.writeValueAsString(teslaStock);
            xml += "</teslaStocks>";

            String xsdPath = "src\\main\\resources\\schema\\tesla\\tesla_schema.xsd";

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));

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
    @ApiOperation(value = "Create a teslaStock resource")
    public void createTeslaStock(@RequestBody TeslaStock teslaStock)
    {
        if (isTeslaStockValidXml(teslaStock) || isTeslaStockValidJson(teslaStock))
        {
            System.out.println(teslaStock.getClose());
            this.teslaStockService.createTeslaStock(teslaStock);
        }
    }

    @GetMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single teslaStock resource based on date")
    public TeslaStock getTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                    @PathVariable("date") Date date)
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));
        if (isTeslaStockValidXml(teslaStockService.getTeslaStock(date)) | isTeslaStockValidJson(teslaStockService.getTeslaStock(date)))
        {
            return teslaStockService.getTeslaStock(date);
        }
        return null;
    }

    @GetMapping(value = "/xml", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all teslaStock resources")
    public Iterable<TeslaStock> getTeslaStocksAsXml()
    {
        checkResourceFound(teslaStockService.getTeslaStocks());
        teslaStockService.getTeslaStocks().forEach(this::isTeslaStockValidXml);
        return teslaStockService.getTeslaStocks();
    }

    @GetMapping(value = "/json", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all teslaStock resources")
    public Iterable<TeslaStock> getTeslaStocksAsJson()
    {
        checkResourceFound(teslaStockService.getTeslaStocks());
        teslaStockService.getTeslaStocks().forEach(this::isTeslaStockValidJson);
        return teslaStockService.getTeslaStocks();
    }

    @PutMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing teslaStock resource")
    public void updateTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("date") Date date,
                                 @RequestBody TeslaStock teslaStock) throws DataFormatException
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));

        if (date != teslaStock.getDate()) throw new DataFormatException("Date does not match!");

        if (isTeslaStockValidXml(teslaStock) || isTeslaStockValidJson(teslaStock))
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
