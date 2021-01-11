package org.steffen.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.steffen.domain.TeslaStock;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TeslaStockRepository extends PagingAndSortingRepository<TeslaStock, Date>
{
    TeslaStock findByDate(Date date);

    @Override
    List<TeslaStock> findAll();
}
