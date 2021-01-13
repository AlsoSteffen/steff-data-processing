//package org.steffen.domain.tweets;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Entity
//@Table(name = "mentions")
//@JsonIgnoreProperties("tweets")
//public class Mention
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column
//    private String mention;
//
//    @ManyToMany(mappedBy = "mentions", cascade = CascadeType.MERGE)
//    private List<Tweet> tweets;
//
//    public long getId()
//    {
//        return id;
//    }
//
//    public void setId(long id)
//    {
//        this.id = id;
//    }
//
//    public String getMention()
//    {
//        return mention;
//    }
//
//    public void setMention(String mention)
//    {
//        this.mention = mention;
//    }
//
//    public List<Tweet> getTweets()
//    {
//        return tweets;
//    }
//
//    public void setTweets(List<Tweet> tweets)
//    {
//        this.tweets = tweets;
//    }
//}
