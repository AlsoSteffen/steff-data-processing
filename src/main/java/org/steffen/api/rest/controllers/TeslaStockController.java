package org.steffen.api.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.domain.TeslaStock;
import org.steffen.services.TeslaStockService;

import java.time.LocalDate;
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

    @PostMapping(value = "",
                 consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a teslaStock resource")
    public void createTeslaStock(@RequestBody TeslaStock teslaStock)
    {
        this.teslaStockService.createTeslaStock(teslaStock);
    }

    @GetMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single teslaStock resource")
    public TeslaStock getTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                    @PathVariable("date") Date date)
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));
        return teslaStockService.getTeslaStock(date);
    }

    @GetMapping(value = "")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all teslaStock resources")
    public Iterable<TeslaStock> getTeslaStock()
    {
        checkResourceFound(teslaStockService.getTeslaStocks());
        return teslaStockService.getTeslaStocks();
    }

    @PutMapping(value = "{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update and existing teslaStock resource")
    public void updateTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("date") Date date,
                                 @RequestBody TeslaStock teslaStock) throws DataFormatException
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));

        if (date != teslaStock.getDate()) throw new DataFormatException("Date does not match!");

        this.teslaStockService.updateTeslaStock(teslaStock);
    }

    @DeleteMapping(value = "{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single teslaStock resource")
    public void deleteTeslaStock(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("date") Date date)
    {
        checkResourceFound(teslaStockService.getTeslaStock(date));
        teslaStockService.deleteTeslaStock(date);
    }
}
