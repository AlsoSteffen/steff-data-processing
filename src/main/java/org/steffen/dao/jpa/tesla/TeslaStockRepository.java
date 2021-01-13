package org.steffen.dao.jpa.tesla;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.steffen.domain.tesla.TeslaStock;

import java.util.Date;
import java.util.List;

@Repository
public interface TeslaStockRepository extends PagingAndSortingRepository<TeslaStock, Date>
{
    TeslaStock findByDate(Date date);

    List<TeslaStock> findAllByDate(Date date);

    @Override
    List<TeslaStock> findAll();
}
