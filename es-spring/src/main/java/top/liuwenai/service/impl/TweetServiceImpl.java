package top.liuwenai.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import top.liuwenai.domain.Tweet;
import top.liuwenai.repository.TweetRepository;
import top.liuwenai.service.TweetService;
@Service
public class TweetServiceImpl implements TweetService {
	
	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public Tweet findById(String id) {
		Optional<Tweet> tweetOpt = tweetRepository.findById(id);
		if(tweetOpt.isPresent())
			return tweetOpt.get();
		return null;
	}
	
	@Override
	public Tweet save(Tweet tweet) {
		return tweetRepository.save(tweet);
	}

	@Override
	public List<Tweet> findByName(String name) {
		return tweetRepository.findByName(name);
	}

	@Override
	public Page<Tweet> findByName(String name, Pageable pageable) {
		return tweetRepository.findByName(name, pageable);
	}

	@Override
	public List<Tweet> findByMessage(String message) {
		return tweetRepository.findByMessage(message);
	}

}
