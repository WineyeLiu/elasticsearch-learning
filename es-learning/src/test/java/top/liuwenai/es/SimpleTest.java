package top.liuwenai.es;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 测试elasticsearch的简单crud操作<br>
 * reference to <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/index.html">Elastic Java API</a>
 * @author liuwenai
 *
 */
public class SimpleTest {
	
	private Logger log = LogManager.getLogger(SimpleTest.class);
	
	TransportClient client;
	
	@SuppressWarnings("resource")
	@Before
	public void initClient() throws UnknownHostException {
		
		log.info("initing transport client");
		
		Settings settings = Settings.builder()
				.put("client.transport.ignore_cluster_name", true)
				.put("client.transport.ping_timeout", "30s")
				.put("client.transport.sniff", true).build();
		client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.146.128"), 9300));
		
		log.info("init completed!");
		List<DiscoveryNode> nodes = client.connectedNodes();
		log.info("connected nodes ("+nodes.size()+") :"+nodes);
	}

	@Test
	public void index() {
		Map<String, Object> testDoc = new HashMap<>();
		testDoc.put("name", "孔夫子");
		testDoc.put("message", "学而时习之，不亦说乎");
		IndexResponse idxResponse =  client.prepareIndex("twitter", "tweet", "9").setSource(testDoc).get();
		log.info("index:"+idxResponse.getIndex()+
				", type="+idxResponse.getType()+
				", id="+idxResponse.getId()+
				", version="+idxResponse.getVersion()+
				", status="+ idxResponse.status());
	}
	
	@Test
	public void get() {
		GetResponse gtResponse =  client.prepareGet("twitter", "tweet", "8").get();
		String resultSource = gtResponse.getSourceAsString();
		log.info("get result: "+resultSource);
	}
	
	@Test
	public void delete() {
		DeleteResponse delResponse = client.prepareDelete("twitter", "tweet", "9").get();
		RestStatus delResult = delResponse.status();
		log.info(delResult);
	}
	
	@After
	public void closeResources() {
		log.info("releasing resources...");
		client.close();
	}
	

}
