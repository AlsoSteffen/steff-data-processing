//package org.steffen.services.tweets;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.steffen.dao.jpa.tweets.MentionRepository;
//import org.steffen.domain.tweets.Mention;
//
//import java.util.List;
//
//@Service
//public class MentionService
//{
//    @Autowired
//    MentionRepository mentionRepository;
//
//    public void createMention(Mention mention)
//    {
//        mentionRepository.save(mention);
//    }
//
//    public Mention getMention(Long id)
//    {
//        return  mentionRepository.findOne(id);
//    }
//
//    public List<Mention> getMentions()
//    {
//        return mentionRepository.findAll();
//    }
//
//    public void updateMention(Mention mention)
//    {
//        mentionRepository.save(mention);
//    }
//
//    public void deleteMention(long id)
//    {
//        mentionRepository.delete(id);
//    }
//}
