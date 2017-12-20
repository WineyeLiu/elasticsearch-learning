package top.liuwenai.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import top.liuwenai.domain.Tweet;

public interface TweetRepository extends ElasticsearchRepository<Tweet, String> {
	
	List<Tweet> findByName(String name);
	
	Page<Tweet> findByName(String name, Pageable pageable);
	
	@Query("{\"bool\" : {\"must\" : {\"term\" : {\"message\" : \"?0\"}}}}")
	List<Tweet> findByMessage(String message);
}
