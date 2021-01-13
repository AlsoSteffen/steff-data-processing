package org.steffen.dao.jpa.tweets;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.steffen.domain.tweets.Tweet;

import java.util.Date;
import java.util.List;

@Repository
public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long>
{
    Tweet findById(Long id);

    Tweet findByDate(Date date);

    List<Tweet> findAllByDate(Date date);

    @Override
    List<Tweet> findAll();
}
