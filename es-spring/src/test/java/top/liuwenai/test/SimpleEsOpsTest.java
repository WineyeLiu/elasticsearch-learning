package top.liuwenai.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.liuwenai.domain.Tweet;
import top.liuwenai.service.TweetService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application.xml")
public class SimpleEsOpsTest {
	
	private Logger log = LogManager.getLogger(SimpleEsOpsTest.class);
	
	@Autowired
	private TweetService tweetService;
	
	@Test
	public void save() {
		Tweet tweet = new Tweet();
		tweet.setId("667");
		tweet.setName("test1");
		tweet.setMessage("test message1.1");
		tweetService.save(tweet);
	}
	
	@Test
	public void findByName1() {
		List<Tweet> list = tweetService.findByName("test1");
		log.info(list);
	}
	
	@Test
	public void findByName2() { //has some problem
		//Sort sort = new Sort(Direction.DESC, "name"); //fielddata is disable on text fields by default.
		Pageable pageable = new PageRequest(1, 10); 
		Page<Tweet> page = tweetService.findByName("liuwenai", pageable);
		log.info(page);
	}
	
	@Test
	public void findByMessage() {
		List<Tweet> list = tweetService.findByMessage("test");
		log.info(list);
	}
	

}
