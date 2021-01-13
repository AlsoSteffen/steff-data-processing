package org.steffen.services.tesla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.steffen.dao.jpa.tesla.TeslaStockRepository;
import org.steffen.domain.tesla.TeslaStock;

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
        return  teslaStockRepository.findByDate(date);
    }

    public List<TeslaStock> getTeslaStocks()
    {
        return teslaStockRepository.findAll();
    }

    public List<TeslaStock> getTeslaStocks(Date date)
    {
        return teslaStockRepository.findAllByDate(date);
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
