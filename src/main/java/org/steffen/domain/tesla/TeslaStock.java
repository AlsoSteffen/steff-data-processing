package org.steffen.domain.tesla;


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
@Table(name = "tesla_stock")
@JacksonXmlRootElement(localName = "teslaStock")
public class TeslaStock implements DomainEntity
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
