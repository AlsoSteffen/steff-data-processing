//package org.steffen.dao.jpa.tweets;
//
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Repository;
//import org.steffen.domain.tweets.Tag;
//
//import java.util.List;
//
//@Repository
//public interface TagRepository extends PagingAndSortingRepository<Tag, Long>
//{
//    Tag findById(long id);
//
//    Tag findByTag(String tag);
//
//    boolean existsByTag(String tag);
//
//    @Override
//    List<Tag> findAll();
//}
