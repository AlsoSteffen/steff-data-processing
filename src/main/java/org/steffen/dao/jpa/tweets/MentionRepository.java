//package org.steffen.dao.jpa.tweets;
//
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Repository;
//import org.steffen.domain.tweets.Mention;
//
//import java.util.List;
//
//@Repository
//public interface MentionRepository extends PagingAndSortingRepository<Mention, Long>
//{
//    Mention findById(long id);
//
//    @Override
//    List<Mention> findAll();
//
//    boolean existsByMention(String mention);
//}
