package org.steffen.domain.netflix;


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
@Table(name = "netflix")
@JacksonXmlRootElement(localName = "netflix")
public class Netflix implements DomainEntity
{
    @Id
    private long id;

    @Column
    private String titleType;

    @Column
    private String title;

    @Column
    private String director;

    @Column
    private String cast;

    @Column
    private String country;

    @Column
    private Date dateAdded;

    @Column
    private int releaseYear;

    @Column
    private String rating;

    @Column
    private String duration;

    @Column
    private String category;

    @Column
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

    /**
     * This setter uses Date Format MMMM dd, yyyy because of the
     * format used in the data set
     * @param date String date to convert to epoch time format
     */
    public void setDateAdded(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

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
