package org.steffen.services.netflix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.steffen.dao.jpa.netflix.NetflixRepository;
import org.steffen.domain.netflix.Netflix;

import java.util.Date;
import java.util.List;

@Service
public class NetflixService
{
    @Autowired
    NetflixRepository netflixRepository;

    public void createNetflix(Netflix netflix)
    {
        netflixRepository.save(netflix);
    }

    public Netflix getNetflix(Long id)
    {
        return  netflixRepository.findOne(id);
    }

    public Netflix getNetflix(Date date)
    {
        return  netflixRepository.findByDateAdded(date);
    }

    public List<Netflix> getNetflix()
    {
        return netflixRepository.findAll();
    }

    public void updateNetflix(Netflix netflix)
    {
        netflixRepository.save(netflix);
    }

    public void deleteNetflix(long id)
    {
        netflixRepository.delete(id);
    }
}
