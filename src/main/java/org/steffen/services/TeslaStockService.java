package org.steffen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.steffen.dao.jpa.TeslaStockRepository;
import org.steffen.domain.TeslaStock;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TeslaStockService
{
    @Autowired
    TeslaStockRepository teslaStockRepository;

    public void createTeslaStock(TeslaStock teslaStock)
    {
        teslaStockRepository.save(teslaStock);
    }

    public TeslaStock getTeslaStock(Date date)
    {
        return  teslaStockRepository.findOne(date);
    }

    public List<TeslaStock> getTeslaStocks()
    {
        return teslaStockRepository.findAll();
    }

    public void updateTeslaStock(TeslaStock teslaStock)
    {
        teslaStockRepository.save(teslaStock);
    }

    public void deleteTeslaStock(Date date)
    {
        teslaStockRepository.delete(date);
    }
}
