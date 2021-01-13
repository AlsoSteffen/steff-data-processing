package org.steffen.api.rest.controllers.netflix;

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
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.netflix.Netflix;
import org.steffen.services.netflix.NetflixService;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/netflix")
@Api(tags = {"netflix"})
public class NetflixController extends AbstractRestHandler
{
    @Autowired
    NetflixService netflixService;

    private final String requireSpecificStock = "The date of an existing netflix resource";

    @PostMapping(value = "",
                 consumes = {"application/json"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create a netflix resource")
    public void createNetflix(@RequestBody Netflix netflix)
    {
        ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();
        try
        {
            File jsonSchemaFile = new File("src\\main\\resources\\schema\\netflix\\netflix_schema.json");
            URI uri = jsonSchemaFile.toURI();

            JsonNode jsonNode = objectMapper.valueToTree(netflix);
            JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(uri.toString());
            ProcessingReport validationResult = jsonSchema.validate(jsonNode);

            // print validation errors
            if (validationResult.isSuccess())
            {
                System.out.println("no validation errors :^)");
                this.netflixService.createNetflix(netflix);
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
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single netflix resource")
    public Netflix getNetflix(@ApiParam(value = requireSpecificStock, required = true)
                              @PathVariable("id") Long id)
    {
        checkResourceFound(netflixService.getNetflix(id));
        return netflixService.getNetflix(id);
    }

    @GetMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single netflix resource")
    public Netflix getNetflix(@ApiParam(value = requireSpecificStock, required = true)
                              @PathVariable("date") Date date)
    {
        checkResourceFound(netflixService.getNetflix(date));
        return netflixService.getNetflix(date);
    }

    @GetMapping(value = "")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all netflix resources")
    public Iterable<Netflix> getNetflixs()
    {
        checkResourceFound(netflixService.getNetflix());
        return netflixService.getNetflix();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing netflix resource")
    public void updateNetflix(@ApiParam(value = requireSpecificStock, required = true)
                              @PathVariable("id") Long id,
                              @RequestBody Netflix netflix) throws DataFormatException
    {
        checkResourceFound(netflixService.getNetflix(id));

        if (id != netflix.getId()) throw new DataFormatException("Date does not match!");

        this.netflixService.updateNetflix(netflix);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single netflix resource")
    public void deleteNetflix(@ApiParam(value = requireSpecificStock, required = true)
                              @PathVariable("id") Long id)
    {
        checkResourceFound(netflixService.getNetflix(id));
        netflixService.deleteNetflix(id);
    }
}
