package org.steffen.domain;


import com.mchange.v2.cfg.PropertiesConfigSource;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "tesla_stock")
public class TeslaStock
{
    @Id
    private Date date;

    @Column
    private double open;

    @Column
    private double high;

    @Column
    private double low;

    @Column
    private double close;

    @Column
    private long volume;

    @Column
    private double adjClose;

    public Date getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        try
        {
            this.date = format.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public double getOpen()
    {
        return open;
    }

    public void setOpen(double open)
    {
        this.open = open;
    }

    public double getHigh()
    {
        return high;
    }

    public void setHigh(double high)
    {
        this.high = high;
    }

    public double getLow()
    {
        return low;
    }

    public void setLow(double low)
    {
        this.low = low;
    }

    public double getClose()
    {
        return close;
    }

    public void setClose(double close)
    {
        this.close = close;
    }

    public long getVolume()
    {
        return volume;
    }

    public void setVolume(long volume)
    {
        this.volume = volume;
    }

    public double getAdjClose()
    {
        return adjClose;
    }

    public void setAdjClose(double adjClose)
    {
        this.adjClose = adjClose;
    }
}
