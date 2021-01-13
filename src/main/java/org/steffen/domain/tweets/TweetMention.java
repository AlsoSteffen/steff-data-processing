//package org.steffen.domain.tweets;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "tweet_mentions")
//public class TweetMention
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToOne
//    @JoinColumn(name = "tweet_id")
//    @JsonIgnoreProperties("tweetsMentions")
//    private Tweet tweetId;
//
//    @ManyToOne
//    @JoinColumn(name = "mention_id")
//    @JsonIgnoreProperties("tweetsMentions")
//    private Mention mentionId;
//
//    public long getId()
//    {
//        return id;
//    }
//
//    public void setId(long id)
//    {
//        this.id = id;
//
//    }
//
//    public Tweet getTweetId()
//    {
//        return tweetId;
//    }
//
//    public void setTweetId(Tweet tweetId)
//    {
//        this.tweetId = tweetId;
//    }
//
//    public Mention getMentionId()
//    {
//        return mentionId;
//    }
//
//    public void setMentionId(Mention mentionId)
//    {
//        this.mentionId = mentionId;
//    }
//}
