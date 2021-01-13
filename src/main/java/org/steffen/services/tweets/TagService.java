//package org.steffen.services.tweets;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.steffen.dao.jpa.tweets.TagRepository;
//import org.steffen.domain.tweets.Tag;
//
//import java.util.List;
//
//@Service
//public class TagService
//{
//    @Autowired
//    TagRepository tagRepository;
//
//    public void createTag(Tag tag)
//    {
//        tagRepository.save(tag);
//    }
//
//    public Tag getTag(Long id)
//    {
//        return  tagRepository.findOne(id);
//    }
//
//    public List<Tag> getTags()
//    {
//        return tagRepository.findAll();
//    }
//
//    public void updateTag(Tag tag)
//    {
//        tagRepository.save(tag);
//    }
//
//    public void deleteTag(long id)
//    {
//        tagRepository.delete(id);
//    }
//}
