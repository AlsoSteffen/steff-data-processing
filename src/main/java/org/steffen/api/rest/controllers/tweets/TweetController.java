package org.steffen.api.rest.controllers.tweets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.steffen.api.rest.controllers.AbstractRestHandler;
import org.steffen.domain.tweets.Tweet;
import org.steffen.services.tweets.TweetService;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/tweet")
@Api(tags = {"tweet"})
public class TweetController extends AbstractRestHandler
{
    @Autowired
    TweetService tweetService;

    private final String requireSpecificStock = "The date of an existing tweet resource";

    @PostMapping(value = "",
                 consumes = {"application/json"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create a tweet resource")
    public void createTweet(@RequestBody Tweet tweet)
    {
        ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();
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
                System.out.println("no validation errors :^)");
                this.tweetService.createTweet(tweet);
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
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource")
    public Tweet getTweet(@ApiParam(value = requireSpecificStock, required = true)
                                    @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        return tweetService.getTweet(id);
    }

    @GetMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get a single tweet resource")
    public Tweet getTweet(@ApiParam(value = requireSpecificStock, required = true)
                                    @PathVariable("date") Date date)
    {
        checkResourceFound(tweetService.getTweet(date));
        return tweetService.getTweet(date);
    }

    @GetMapping(value = "")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get all tweet resources")
    public Iterable<Tweet> getTweets()
    {
        checkResourceFound(tweetService.getTweets());
        return tweetService.getTweets();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Update any existing tweet resource")
    public void updateTweet(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("id") Long id,
                                 @RequestBody Tweet tweet) throws DataFormatException
    {
        checkResourceFound(tweetService.getTweet(id));

        if (id != tweet.getId()) throw new DataFormatException("Date does not match!");

        this.tweetService.updateTweet(tweet);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete a single tweet resource")
    public void deleteTweet(@ApiParam(value = requireSpecificStock, required = true)
                                 @PathVariable("id") Long id)
    {
        checkResourceFound(tweetService.getTweet(id));
        tweetService.deleteTweet(id);
    }
}
