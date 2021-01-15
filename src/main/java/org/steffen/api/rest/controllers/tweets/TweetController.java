package org.steffen.api.rest.controllers.tweets;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.DomainEntity;
import org.steffen.domain.tweets.Tweet;
import org.steffen.services.tweets.TweetService;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/tweet")
@Api(tags = {"tweet"})
public class TweetController extends AbstractRestHandler
{
    @Autowired
    TweetService tweetService;

    private final String requireSpecificStock = "The date of an existing tweet resource";

    private final File jsonSchemaFile = new File("src\\main\\resources\\schema\\tweets\\tweets_schema.json");
    private final File xmlSchemaFile = new File("src\\main\\resources\\schema\\tweets\\tweets_schema.xsd");

    /**
     * see - AbstractRestHandler.isEntityValidXml
     * @param entity - DomainEntity to check structure
     * @return boolean - whether the xml is valid or not
     */
    @Override
    protected boolean isEntityValidXml(DomainEntity entity)
    {
        try
        {
            XmlMapper xmlMapper = new XmlMapper();
            String xml;
            xml = "<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<tweets   xmlns=\"https://www.w3schools.com\"\n" +
                    "                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "                 xsi:schemaLocation=\"https://www.w3schools.com tweets_schema.xsd\">";
            xml += xmlMapper.writeValueAsString(entity);
            xml += "</tweets>";

            Schema schema = factory.newSchema(xmlSchemaFile);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));

            System.out.println("No validation errors -xml :D");
            return true;
        }
        catch (IOException | SAXException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping(value = "",
                 consumes = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create a tweet resource")
    public void createTweet(@RequestBody Tweet tweet)
    {
        if (isEntityValidXml(tweet) || isEntityValidJson(tweet, jsonSchemaFile))
        {
            this.tweetService.createTweet(tweet);
        }
    }

    @GetMapping(value = "/json/date={date}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource based on date")
    public List<Tweet> getJsonTweetBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                               @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            List<Tweet> validTweets = new ArrayList<>();

            checkResourceFound(tweetService.getTweets(formattedDate));
            tweetService.getTweets(formattedDate).forEach(tweet -> isEntityValidJson(tweet, jsonSchemaFile));
            for (Tweet tweet : tweetService.getTweets(formattedDate))
            {
                if (isEntityValidJson(tweet, jsonSchemaFile))
                {
                    validTweets.add(tweet);
                }
            }
            return validTweets;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/xml/date={date}", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource based on date")
    public List<Tweet> getXmlTweetsBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                               @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            List<Tweet> validTweets = new ArrayList<>();

            checkResourceFound(tweetService.getTweets(formattedDate));
            tweetService.getTweets(formattedDate).forEach(this::isEntityValidXml);
            for (Tweet tweet : tweetService.getTweets(formattedDate))
            {
                if (isEntityValidXml(tweet))
                {
                    validTweets.add(tweet);
                }
            }
            return validTweets;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/json/id={id}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource based on id")
    public Tweet getJsonTweetBasedOnId(@ApiParam(value = requireSpecificStock, required = true)
                                       @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        if (isEntityValidJson(tweetService.getTweet(id), jsonSchemaFile))
        {
            return tweetService.getTweet(id);
        }
        return null;
    }

    @GetMapping(value = "/xml/id={id}", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource based on id")
    public Tweet getXmlTweetBasedOnId(@ApiParam(value = requireSpecificStock, required = true)
                                      @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        if (isEntityValidXml(tweetService.getTweet(id)))
        {
            return tweetService.getTweet(id);
        }
        return null;
    }

    @GetMapping(value = "/xml", produces = {"application/xml"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all tweet resources")
    public Iterable<Tweet> getTweetsAsXml()
    {
        List<Tweet> validTweets = new ArrayList<>();

        checkResourceFound(tweetService.getTweets());
        tweetService.getTweets().forEach(this::isEntityValidXml);
        for (Tweet tweet : tweetService.getTweets())
        {
            if (isEntityValidXml(tweet))
            {
                validTweets.add(tweet);
            }
        }
        return validTweets;
    }

    @GetMapping(value = "/count={date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get the number of tweets in a day")
    public Integer getNumberOnTweetsBasedOnDate(@ApiParam(value = requireSpecificStock, required = true)
                                                    @PathVariable("date") String date)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            checkResourceFound(tweetService.getTweets(formattedDate));
            return tweetService.getTweets(formattedDate).size();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }


    @GetMapping(value = "/json", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all tweet resources")
    public Iterable<Tweet> getTweetsAsJson()
    {
        List<Tweet> validTweets = new ArrayList<>();

        checkResourceFound(tweetService.getTweets());
        tweetService.getTweets().forEach(tweet -> isEntityValidJson(tweet, jsonSchemaFile));
        for (Tweet tweet : tweetService.getTweets())
        {
            if (isEntityValidJson(tweet, jsonSchemaFile))
            {
                validTweets.add(tweet);
            }
        }
        return validTweets;
    }

    @PutMapping(value = "/id={id}", consumes = {"application/xml", "application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing tweet resource based on id")
    public void updateTweet(@ApiParam(value = requireSpecificStock, required = true)
                            @PathVariable("id") Long id,
                            @RequestBody Tweet tweet) throws DataFormatException
    {
        checkResourceFound(tweetService.getTweet(id));

        if (id != tweet.getId()) throw new DataFormatException("Id does not match!");

        if (isEntityValidXml(tweet) || isEntityValidJson(tweet, jsonSchemaFile))
        {
            this.tweetService.updateTweet(tweet);
        }
    }

    @PutMapping(value = "/date={date}", consumes = {"application/xml", "application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing tweet resource based on date")
    public void updateTweet(@ApiParam(value = requireSpecificStock, required = true)
                            @PathVariable("date") String date,
                            @RequestBody Tweet tweet)
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            checkResourceFound(tweetService.getTweet(formattedDate));

            if (isEntityValidXml(tweetService.getTweet(formattedDate)))
            {
                tweetService.updateTweet(tweet);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "/id={id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single tweet resource")
    public void deleteTweet(@ApiParam(value = requireSpecificStock, required = true)
                            @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        tweetService.deleteTweet(id);
    }
}
