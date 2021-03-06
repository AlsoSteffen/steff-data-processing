package org.steffen.domain.tweets;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.steffen.domain.DomainEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tweets")
@JacksonXmlRootElement(localName = "tweet")
@JsonIgnoreProperties("tweets")
public class Tweet implements DomainEntity
{
    @Id
    private long id;

    @Column
    private String link;

    @Column
    private String content;

    @Column
    private Date date;

    @Column
    private int retweets;

    @Column
    private int favorites;

    @Column
    private String mentions;

    @Column
    private String tags;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getDate()
    {
        return date;
    }


    public void setDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            this.date = format.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public int getRetweets()
    {
        return retweets;
    }

    public void setRetweets(int retweets)
    {
        this.retweets = retweets;
    }

    public int getFavorites()
    {
        return favorites;
    }

    public void setFavorites(int favorites)
    {
        this.favorites = favorites;
    }

    public String getMentions()
    {
        return mentions;
    }

    public void setMentions(String mentions)
    {
        this.mentions = mentions;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }
}
