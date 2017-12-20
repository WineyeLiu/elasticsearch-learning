package top.liuwenai.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import top.liuwenai.domain.Tweet;

public interface TweetService {
	
	Tweet findById(String id);
	
	Tweet save(Tweet tweet);
	
	List<Tweet> findByName(String name);
	
	Page<Tweet> findByName(String name, Pageable pageable);
	
	List<Tweet> findByMessage(String message);
}
