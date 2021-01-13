package org.steffen.api.rest.controllers.tweets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.tweets.Tweet;
import org.steffen.services.tweets.TweetService;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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
    
    
    private boolean isTweetValidJson(Tweet tweet)
    {
        try
        {
            File jsonSchemaFile = new File("src\\main\\resources\\schema\\tweets\\tweets_schema.json");
            URI uri = jsonSchemaFile.toURI();

            JsonNode jsonNode = objectMapper.valueToTree(tweet);
            JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(uri.toString());
            ProcessingReport validationResult = jsonSchema.validate(jsonNode);

            // print validation errors
            if (validationResult.isSuccess())
            {
                System.out.println("no validation errors - json :^)");
                return true;
            }
            else
            {
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            }
        }
        catch (ProcessingException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isTweetValidXml(Tweet tweet)
    {
        try
        {
            XmlMapper xmlMapper = new XmlMapper();
            String xml;
            xml = "<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<tweets   xmlns=\"https://www.w3schools.com\"\n" +
                    "                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "                 xsi:schemaLocation=\"https://www.w3schools.com tweets_schema.xsd\">";
            xml += xmlMapper.writeValueAsString(tweet);
            xml += "</tweets>";

            String xsdPath = "src\\main\\resources\\schema\\tweets\\tweets_schema.xsd";

            Schema schema = factory.newSchema(new File(xsdPath));

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
        if (isTweetValidXml(tweet) || isTweetValidJson(tweet))
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

            tweetService.getTweets(formattedDate).forEach(AbstractRestHandler::checkResourceFound);
            tweetService.getTweets(formattedDate).forEach(this::isTweetValidJson);

            return tweetService.getTweets(formattedDate);
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

            tweetService.getTweets(formattedDate).forEach(AbstractRestHandler::checkResourceFound);
            tweetService.getTweets(formattedDate).forEach(this::isTweetValidXml);

            return tweetService.getTweets(formattedDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/json/{id}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource based on id")
    public Tweet getJsonTweetBasedOnId(@ApiParam(value = requireSpecificStock, required = true)
                                    @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        if (isTweetValidJson(tweetService.getTweet(id)))
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
        if (isTweetValidXml(tweetService.getTweet(id)))
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
        checkResourceFound(tweetService.getTweets());
        tweetService.getTweets().forEach(this::isTweetValidXml);
        return tweetService.getTweets();
    }

    @GetMapping(value = "/json", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all tweet resources")
    public Iterable<Tweet> getTweetsAsJson()
    {
        checkResourceFound(tweetService.getTweets());
        tweetService.getTweets().forEach(this::isTweetValidJson);
        return tweetService.getTweets();
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

        if (isTweetValidXml(tweet) || isTweetValidJson(tweet))
        {
            this.tweetService.updateTweet(tweet);
        }
    }

    @PutMapping(value = "/date={date}", consumes = {"application/xml", "application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing tweet resource based on date")
    public void updateTweet(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("date") String date,
                                 @RequestBody Tweet tweet) throws DataFormatException
    {
        try
        {
            Date formattedDate = dateFormat.parse(date);

            checkResourceFound(tweetService.getTweet(formattedDate));

            if (isTweetValidXml(tweetService.getTweet(formattedDate)))
            {
                tweetService.updateTweet(tweet);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "?id={id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single tweet resource")
    public void deleteTweet(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        tweetService.deleteTweet(id);
    }
}
