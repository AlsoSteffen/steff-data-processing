//package org.steffen.services.tweets;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.steffen.dao.jpa.tweets.TweetMentionRepository;
//import org.steffen.domain.tweets.Mention;
//import org.steffen.domain.tweets.Tweet;
//import org.steffen.domain.tweets.TweetMention;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class TweetMentionService
//{
//    @Autowired
//    TweetMentionRepository tweetMentionRepository;
//
//    public void createTweetMention(TweetMention TweetMention)
//    {
//        tweetMentionRepository.save(TweetMention);
//    }
//
//    public TweetMention getTweetMention(Long id)
//    {
//        return  tweetMentionRepository.findOne(id);
//    }
//
//    public List<TweetMention> getTweetMentions()
//    {
//        return tweetMentionRepository.findAll();
//    }
//
//    public void updateTweetMention(TweetMention TweetMention)
//    {
//        tweetMentionRepository.save(TweetMention);
//    }
//
//    public void deleteTweetMention(long id)
//    {
//        tweetMentionRepository.delete(id);
//    }
//}
