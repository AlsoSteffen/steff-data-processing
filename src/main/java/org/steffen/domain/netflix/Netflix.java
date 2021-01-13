package org.steffen.domain.netflix;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "netflix")
@XmlRootElement(name = "title")
public class Netflix
{
    @Id
    @JsonProperty
    @XmlElement
    private long id;

    @Column
    @JsonProperty
    @XmlElement
    private String titleType;

    @Column
    @JsonProperty
    @XmlElement
    private String title;

    @Column
    @JsonProperty
    @XmlElement
    private String director;

    @Column
    @JsonProperty
    @XmlElement
    private String cast;

    @Column
    @JsonProperty
    @XmlElement
    private String country;

    @Column
    @JsonProperty
    @XmlElement
    private Date dateAdded;

    @Column
    @JsonProperty
    @XmlElement
    private int releaseYear;

    @Column
    @JsonProperty
    @XmlElement
    private String rating;

    @Column
    @JsonProperty
    @XmlElement
    private String duration;

    @Column
    @JsonProperty
    @XmlElement
    private String category;

    @Column
    @JsonProperty
    @XmlElement
    private String description;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitleType()
    {
        return titleType;
    }

    public void setTitleType(String type)
    {
        this.titleType = type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public String getCast()
    {
        return cast;
    }

    public void setCast(String cast)
    {
        this.cast = cast;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Date getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            this.dateAdded = format.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public int getReleaseYear()
    {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear)
    {
        this.releaseYear = releaseYear;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
