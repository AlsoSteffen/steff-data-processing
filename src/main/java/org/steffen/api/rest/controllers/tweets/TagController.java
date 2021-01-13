//package org.steffen.api.rest.controllers.tweets;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.steffen.api.rest.controllers.AbstractRestHandler;
//import org.steffen.domain.tweets.Tag;
//import org.steffen.services.tweets.TagService;
//
//import java.util.zip.DataFormatException;
//
//@RestController
//@RequestMapping(value = "/tag")
//@Api(tags = {"tag"})
//public class TagController extends AbstractRestHandler
//{
//    @Autowired
//    TagService tagService;
//
//    private final String requireSpecificStock = "The date of an existing tag resource";
//
//    @PostMapping(value = "",
//                 consumes = {"application/json"})
//    @ResponseStatus(HttpStatus.CREATED)
//    @ApiOperation(value = "Create a tag resource")
//    public void createTag(@RequestBody Tag tag)
//    {
//        this.tagService.createTag(tag);
//    }
//
//    @GetMapping(value = "/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Get a single tag resource")
//    public Tag getTag(@ApiParam(value = requireSpecificStock, required = true)
//                              @PathVariable("id") Long id)
//    {
//        checkResourceFound(tagService.getTag(id));
//        return tagService.getTag(id);
//    }
//
//    @GetMapping(value = "")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Get all tag resources")
//    public Iterable<Tag> getTag()
//    {
//        checkResourceFound(tagService.getTags());
//        return tagService.getTags();
//    }
//
//    @PutMapping(value = "/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Update any existing tag resource")
//    public void updateTag(@ApiParam(value = requireSpecificStock, required = true)
//                              @PathVariable("id") Long id,
//                              @RequestBody Tag tag) throws DataFormatException
//    {
//        checkResourceFound(tagService.getTag(id));
//
//        if (id != tag.getId()) throw new DataFormatException("Date does not match!");
//
//        this.tagService.updateTag(tag);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "Delete a single tag resource")
//    public void deleteTag(@ApiParam(value = requireSpecificStock, required = true)
//                              @PathVariable("id") Long id)
//    {
//        checkResourceFound(tagService.getTag(id));
//        tagService.deleteTag(id);
//    }
//}
