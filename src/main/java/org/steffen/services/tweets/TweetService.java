package org.steffen.services.tweets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.steffen.dao.jpa.tweets.TweetRepository;
import org.steffen.domain.tweets.Tweet;

import java.util.Date;
import java.util.List;

@Service
public class TweetService
{
    @Autowired
    TweetRepository tweetRepository;

    public void createTweet(Tweet tweet)
    {
        tweetRepository.save(tweet);
    }

    public Tweet getTweet(Long id)
    {
        return  tweetRepository.findOne(id);
    }

    public Tweet getTweet(Date date)
    {
        return tweetRepository.findByDate(date);
    }

    public List<Tweet> getTweets()
    {
        return tweetRepository.findAll();
    }

    public void updateTweet(Tweet tweet)
    {
        tweetRepository.save(tweet);
    }

    public void deleteTweet(long id)
    {
        tweetRepository.delete(id);
    }
}
