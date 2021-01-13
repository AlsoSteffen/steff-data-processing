package org.steffen.dao.jpa.netflix;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.steffen.domain.netflix.Netflix;

import java.util.Date;
import java.util.List;

@Repository
public interface NetflixRepository extends PagingAndSortingRepository<Netflix, Long>
{
    Netflix findById(long id);

    Netflix findByDateAdded(Date date);

    @Override
    List<Netflix> findAll();
}
