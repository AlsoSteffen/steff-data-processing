//package org.steffen.domain.tweets;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "tags")
//@JsonIgnoreProperties("tweets")
//public class Tag
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column
//    private String tag;
//
//    @ManyToMany(mappedBy = "tags", cascade = CascadeType.MERGE)
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
//    public String getTag()
//    {
//        return tag;
//    }
//
//    public void setTag(String mention)
//    {
//        this.tag = mention;
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
