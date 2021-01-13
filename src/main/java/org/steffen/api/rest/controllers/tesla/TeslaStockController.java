package org.steffen.api.rest.controllers.tesla;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.tesla.TeslaStock;
import org.steffen.services.tesla.TeslaStockService;

import java.io.File;
import java.net.URI;
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
                System.out.println("no validation errors :^)");
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

    @PostMapping(value = "",
                 consumes = {"application/json"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create a teslaStock resource")
    public void createTeslaStock(@RequestBody TeslaStock teslaStock)
    {
        if (isTeslaStockValidJson(teslaStock))
        {
            this.teslaStockService.createTeslaStock(teslaStock);
        }
    }

    @GetMapping(value = "/{date}", produces = { "application/json", "application/xml" })
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single teslaStock resource")
    public TeslaStock getTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                    @PathVariable("date") Date date)
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));
        if (isTeslaStockValidJson(teslaStockService.getTeslaStock(date)))
        {
            return teslaStockService.getTeslaStock(date);
        }
        return null;
    }

    @GetMapping(value = "")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all teslaStock resources")
    public Iterable<TeslaStock> getTeslaStock()
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

        if (isTeslaStockValidJson(teslaStock))
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
