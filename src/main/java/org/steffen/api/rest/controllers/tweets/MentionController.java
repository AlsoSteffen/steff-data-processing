//package org.steffen.api.rest.controllers.tweets;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.steffen.api.rest.controllers.AbstractRestHandler;
//import org.steffen.domain.tweets.Mention;
//import org.steffen.services.tweets.MentionService;
//
//import java.util.zip.DataFormatException;
//
//@RestController
//@RequestMapping(value = "/mention")
//@Api(tags = {"mention"})
//public class MentionController extends AbstractRestHandler
//{
//    @Autowired
//    MentionService mentionService;
//
//    private final String requireSpecificStock = "The date of an existing mention resource";
//
//    @PostMapping(value = "",
//                 consumes = {"application/json"})
//    @ResponseStatus(HttpStatus.CREATED)
//    @ApiOperation(value = "Create a mention resource")
//    public void createMention(@RequestBody Mention mention)
//    {
//        this.mentionService.createMention(mention);
//    }
//
//    @GetMapping(value = "/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Get a single mention resource")
//    public Mention getMention(@ApiParam(value = requireSpecificStock, required = true)
//                          @PathVariable("id") Long id)
//    {
//        checkResourceFound(mentionService.getMention(id));
//        return mentionService.getMention(id);
//    }
//
//    @GetMapping(value = "")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Get all mention resources")
//    public Iterable<Mention> getMention()
//    {
//        checkResourceFound(mentionService.getMentions());
//        return mentionService.getMentions();
//    }
//
//    @PutMapping(value = "/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Update any existing mention resource")
//    public void updateMention(@ApiParam(value = requireSpecificStock, required = true)
//                            @PathVariable("id") Long id,
//                            @RequestBody Mention mention) throws DataFormatException
//    {
//        checkResourceFound(mentionService.getMention(id));
//
//        if (id != mention.getId()) throw new DataFormatException("Date does not match!");
//
//        this.mentionService.updateMention(mention);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Delete a single mention resource")
//    public void deleteMention(@ApiParam(value = requireSpecificStock, required = true)
//                            @PathVariable("id") Long id)
//    {
//        checkResourceFound(mentionService.getMention(id));
//        mentionService.deleteMention(id);
//    }
//}
